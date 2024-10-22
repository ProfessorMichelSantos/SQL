package com.example.conexaosql

import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConSQL {

    private val ip="192.168.100.40:1433"
    private val db="biblioteca"
    private val username="estudante"
    private val senha="123456"

    fun dbCon(): Connection? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var conn:Connection? = null
        val connString : String
        try{
           Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()
           connString = "jdbc:jtds:sqlserver://$ip;databaseName=$db;user=$username;password=$senha"
           conn=DriverManager.getConnection(connString)
        }catch (ex: SQLException) {
            Log.e("Error: ", ex.message!!)
        }catch (ex1: ClassNotFoundException) {
            Log.e("Error: ", ex1.message!!)
        }catch (ex2: Exception) {
            Log.e("Error: ", ex2.message!!)
        }

        return conn
    }

}