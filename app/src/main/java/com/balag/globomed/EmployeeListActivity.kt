package com.balag.globomed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.globomed.learn.EmployeeListAdapter

import kotlinx.android.synthetic.main.activity_employee_list.*
import kotlinx.android.synthetic.main.content_employee_list.*

class EmployeeListActivity : AppCompatActivity() {

    lateinit var databaseHelper: DatabaseHelper
    private val employeeListAdapter = EmployeeListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)
        setSupportActionBar(toolbar)

        databaseHelper = DatabaseHelper(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = employeeListAdapter
        employeeListAdapter.setEmployees(DataManager.fetchAllEmployees(databaseHelper))

        fab.setOnClickListener { view ->
            val addEmployeeActivity = Intent(this,AddEmployeeActivity::class.java)
            startActivityForResult(addEmployeeActivity,1)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            employeeListAdapter.setEmployees(DataManager.fetchAllEmployees(databaseHelper))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_deleteAll -> {
                val builder = androidx.appcompat.app.AlertDialog.Builder(this)
                builder.setMessage(R.string.confirm_sure)
                    .setPositiveButton(R.string.yes) { dialog, eId ->
                        val result = DataManager.deleteAllEmployee(databaseHelper)
                        Toast.makeText(applicationContext,"$result deleted ", Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK,Intent())
                        finish()
                    }
                    .setNegativeButton(R.string.no) { dialog, id ->
                        dialog.dismiss()
                    }


                val dialog = builder.create()
                dialog.setTitle("Confirmation")
                dialog.show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}
