package com.example.conexaosql

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

lateinit var matricula: EditText
lateinit var nomealuno: EditText
lateinit var btnLeitura: Button


class MainActivity : AppCompatActivity() {

    private var conexao = ConSQL()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listarvendas()
        matricula = findViewById(R.id.id)
        nomealuno = findViewById(R.id.nome)
        btnLeitura = findViewById(R.id.btnler)

        btnLeitura.setOnClickListener {
            try{
                val adicionaaluno: PreparedStatement = conexao.dbCon()?.prepareStatement("insert into aluno values(?,?)")!!
                adicionaaluno.setString(1, matricula.text.toString())
                adicionaaluno.setString(2, nomealuno.text.toString())
                adicionaaluno.executeUpdate()

                Toast.makeText(this,"Aluno adicionado", Toast.LENGTH_SHORT).show()

            }catch (ex: SQLException){
                Toast.makeText(this, "Erro ", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun listarvendas()
    {

        // Configurando o LayoutManager e o Adapter
        var adapter: RecyclerViewAdapter? = null
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)


            val alunos = ArrayList<Aluno>()
            val statement = conexao.dbCon()?.createStatement()

            val resultset: ResultSet = statement!!.executeQuery("SELECT * FROM ALUNO")


        while (resultset.next()) {
            val aluno = Aluno(
                idaluno = resultset.getInt("id"),
                nomealuno = resultset.getString("nome")
            )
            alunos.add(aluno)
        }



        resultset.close()
        statement.close()
        conexao.dbCon()?.close()

        adapter = RecyclerViewAdapter(alunos)
        recyclerView.adapter = adapter


    }
}
