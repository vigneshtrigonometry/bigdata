
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SparkSession
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Connection
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.SaveMode
import java.util.Properties
import java.sql.Statement
import org.apache.hive.jdbc.HiveStatement
import org.apache.spark.sql.functions.lit
import com.google.gson.Gson
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.avro.data.Json
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import java.io._
import org.apache.commons._
import org.apache.http._
import org.apache.http.client._
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import java.util.ArrayList
import com.google.gson.Gson

case class fruadData(lat: String, lon: String, fraud: String)

object Main {
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("Fraud Detection").setMaster("local")
    val sc = new SparkContext(conf);

    val sqlCtx = new SQLContext(sc)
    //val hiveContext = new org.apache.spark.sql.hive.HiveContext(sc)

    //file:///c:/tmp/spark-warehouse
    val spark = SparkSession.builder().appName("Fraud Detection")
      .config("spark.sql.warehouse.dir", "/home/training/workspace/FraudDetectionBigData")
      .enableHiveSupport()
      .getOrCreate()

    val transPath = "hdfs://localhost:8020/loudacre/bigdata/fraud"

    val driverName = "org.apache.hive.jdbc.HiveDriver"
    Class.forName(driverName)
    val conn: Connection = DriverManager.getConnection("jdbc:hive2://192.168.40.131:10000/bigdata", "hive", "")
    val statement: Statement = conn.createStatement();

    val transDF = spark.read
      .format("com.databricks.spark.csv")
      .option("header", "true") //reading the headers
      .option("mode", "DROPMALFORMED")
      .load(transPath + "/transaction.csv");

    transDF.createOrReplaceTempView("transaction")
    /***************DATA INGESTION FOR TRANSACTION **************************************/
//        var strzip: String = ""
//        var i:Int =0 
//        transDF.take(100000).foreach { x =>
//          strzip += "(" + x.getString(0) + "," + x.getString(1) + "," + x.getString(2) + "," + x.getString(3) + "," + x.getString(4) + ",NULL),"
//          println(i)
//          i+=1;
//          if(i==3000){
//            println(strzip)
//            strzip += "(" + x.getString(0) + "," + x.getString(1) + "," + x.getString(2) + "," + x.getString(3) + "," + x.getString(4) + ",NULL)"
//             statement.executeUpdate("insert into table transactions values " + strzip);
//            strzip = ""
//            i=0
//            
//          }
//        }
//        strzip += "(1,1,1,1,1,NULLl)"
//        statement.executeUpdate("insert into table transactions values " + strzip);

    val posDF = spark.read
      .format("com.databricks.spark.csv")
      .option("header", "true") //reading the headers
      .option("mode", "DROPMALFORMED")
      .option("escape", "'")
      .load(transPath + "/pos_device.csv");
    posDF.createOrReplaceTempView("device");

    /***************DATA INGESTION FOR POS_DEVICES **************************************/
    //    var strpos: String = ""
    //    posDF.take(posDF.count().toInt).foreach { x =>
    //      strpos += "(" + x.getString(0) + ",'" + x.getString(1) + "','" + x.getString(2) + "'),"
    //    }
    //    strpos += "(" + 1 + ",'" + 1 + "','" + 1 + "')"
    //    println(strpos)
    //    statement.executeUpdate("insert into table devices values " + strpos);

    val zipDF = spark.read
      .format("com.databricks.spark.csv")
      .option("header", "true") //reading the headers
      .option("nullValue", "null")
      .option("treatEmptyValuesAsNulls", "true")
      .option("quote", "")
      .option("mode", "DROPMALFORMED")
      .option("escape", "'")
      .load(transPath + "/zip_codes_states.csv");

    zipDF.createOrReplaceTempView("location")
    /******************DATA INGESTION FOR ZIP_CODES_LOCATION************/
    //    var strzip: String = ""
    //    var i:Int =0 
    //    zipDF.take(zipDF.count().toInt).foreach { x =>
    //      strzip += "(" + x.getString(0) + "," + x.getString(1) + "," + x.getString(2) + ",\"" + x.getString(3) + "\",\"" + x.getString(4) + "\",\"" + x.getString(5) + "\",\"" + x.getString(6) + "\"),"
    //      println(i)
    //      i+=1;
    //      if(i==3000){
    //        strzip += "(" + x.getString(0) + "," + x.getString(1) + "," + x.getString(2) + ",\"" + x.getString(3) + "\",\"" + x.getString(4) + "\",\"" + x.getString(5) + "\",\"" + x.getString(6) + "\")"
    //         statement.executeUpdate("insert into table location values " + strzip);
    //        strzip = ""
    //        i=0
    //        
    //      }
    //    }
    //    strzip += "(1,1,1,\"1\",\"1\",\"1\",\"1\")"
    //    statement.executeUpdate("insert into table location values " + strzip);

    val testDF = spark.sql("select account_id, first(device_id) as device_id, max(c) as max from (select account_id, device_id, count(*) as c from transaction group by account_id, device_id order by c desc) group by account_id")
    testDF.createOrReplaceTempView("testtab")

    /******************DATA INGESTION FOR TRANSACTION************/
    var strr: String = ""
    transDF.take(100).foreach { x =>
      println("---processing----")
      val client = new DefaultHttpClient
      val post = new HttpPost("http://172.17.251.242:8080/FraudDetection/api/transactions")
      post.setHeader("Content-type", "application/json")
      var loc = spark.sql("select DISTINCT zcs.latitude, zcs.longitude from transaction tr JOIN device pd ON " + x.getString(1) + "= pd.id JOIN location zcs ON CONCAT(zcs.county,' County, ',zcs.name) = pd.location")
      var spock:fruadData = null // = new fruadData(loc.first().getString(0),loc.first().getString(1),"0")
      

      if (spark.sql("select * from testtab t where t.account_id=" + x.getString(3) + " and t.device_id =" + x.getString(1) + " limit 1").count() > 0) {
        strr += "(" + x.getString(0) + "," + x.getString(1) + "," + x.getString(2) + "," + x.getString(3) + "," + x.getString(4) + ",FALSE),"
        if(loc.count()>0) { spock = new fruadData(loc.first().getString(0),loc.first().getString(1),"0") }

      } else {
        strr += "(" + x.getString(0) + "," + x.getString(1) + "," + x.getString(2) + "," + x.getString(3) + "," + x.getString(4) + ",TRUE),"
        if(loc.count()>0) { spock = new fruadData(loc.first().getString(0),loc.first().getString(1),"1") }
      }
      
      println(spock)
      var spockAsJson = new Gson().toJson(spock)
      post.setEntity(new StringEntity(spockAsJson));
      client.execute(post)

    }
    strr += "(" + 1 + "," + 1 + "," + 1 + "," + 1 + "," + 1 + ",FALSE)"
    println(strr)
    statement.executeUpdate("insert into table transactions values " + strr);

    println("Writing into table....")

    println("END !")

    sc.stop
  }
}