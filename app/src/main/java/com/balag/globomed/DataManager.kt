package com.balag.globomed

import android.content.ContentValues
import android.database.Cursor

object DataManager {

    fun fetchAllEmployees(databaseHelper: DatabaseHelper) : ArrayList<Employee>{
        val employees = ArrayList<Employee>()

        val db = databaseHelper.readableDatabase

        val columns = arrayOf(
            GloboMedDbContract.EmployeeEntry.COLUMN_ID,
            GloboMedDbContract.EmployeeEntry.COLUMN_NAME,
            GloboMedDbContract.EmployeeEntry.COLUMN_DESIGNATION,
            GloboMedDbContract.EmployeeEntry.COLUMN_DOB,
            GloboMedDbContract.EmployeeEntry.COLUMN_SURGEON

        )

        val cursor: Cursor = db.query(
            GloboMedDbContract.EmployeeEntry.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null

        )

        val idPosition = cursor.getColumnIndex(GloboMedDbContract.EmployeeEntry.COLUMN_ID)
        val namePosition = cursor.getColumnIndex(GloboMedDbContract.EmployeeEntry.COLUMN_NAME)
        val designationPosition = cursor.getColumnIndex(GloboMedDbContract.EmployeeEntry.COLUMN_DESIGNATION)
        val dobPosition = cursor.getColumnIndex(GloboMedDbContract.EmployeeEntry.COLUMN_DOB)
        val surgeonPosition = cursor.getColumnIndex(GloboMedDbContract.EmployeeEntry.COLUMN_SURGEON)

        while(cursor.moveToNext()) {
            val id = cursor.getString(idPosition)
            val name = cursor.getString(namePosition)
            val designation = cursor.getString(designationPosition)
            val dob = cursor.getLong(dobPosition)
            val surgeon = cursor.getInt(surgeonPosition)
            employees.add(Employee(id,name,dob,designation,surgeon))
        }
        cursor.close()
        return employees
    }

    fun fetchEmployeeByID(databaseHelper: DatabaseHelper, empId: String): Employee? {
        val db = databaseHelper.readableDatabase
        var employee: Employee? = null

        val columns = arrayOf(
            GloboMedDbContract.EmployeeEntry.COLUMN_NAME,
            GloboMedDbContract.EmployeeEntry.COLUMN_DESIGNATION,
            GloboMedDbContract.EmployeeEntry.COLUMN_DOB,
            GloboMedDbContract.EmployeeEntry.COLUMN_SURGEON,
            GloboMedDbContract.EmployeeEntry.COLUMN_SURGEON


        )


        val selection = GloboMedDbContract.EmployeeEntry.COLUMN_ID+" LIKE ?  "
        val selectionArgs = arrayOf(empId)

        val cursor: Cursor = db.query(
            GloboMedDbContract.EmployeeEntry.TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null

        )

        val namePosition = cursor.getColumnIndex(GloboMedDbContract.EmployeeEntry.COLUMN_NAME)
        val designationPosition = cursor.getColumnIndex(GloboMedDbContract.EmployeeEntry.COLUMN_DESIGNATION)
        val dobPosition = cursor.getColumnIndex(GloboMedDbContract.EmployeeEntry.COLUMN_DOB)
        val surgeonPosition = cursor.getColumnIndex(GloboMedDbContract.EmployeeEntry.COLUMN_SURGEON)

        while(cursor.moveToNext()) {
            val name = cursor.getString(namePosition)
            val designation = cursor.getString(designationPosition)
            val dob = cursor.getLong(dobPosition)
            val surgeon = cursor.getInt(surgeonPosition)
            employee = Employee(empId,name,dob,designation,surgeon)
        }
        cursor.close()
        return employee
    }

    fun updateEmployee(databaseHelper: DatabaseHelper, employee: Employee) {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()

        values.put(GloboMedDbContract.EmployeeEntry.COLUMN_NAME,employee.name)
        values.put(GloboMedDbContract.EmployeeEntry.COLUMN_DESIGNATION,employee.designation)
        values.put(GloboMedDbContract.EmployeeEntry.COLUMN_DOB,employee.dob)
        values.put(GloboMedDbContract.EmployeeEntry.COLUMN_SURGEON,employee.isSurgeon )

        val selection : String= GloboMedDbContract.EmployeeEntry.COLUMN_ID+" LIKE ?  "
        val selectionArgs: Array<String> = arrayOf(employee.id)

        db.update(GloboMedDbContract.EmployeeEntry.TABLE_NAME, values,selection,selectionArgs)
    }

    fun deleteEmployee(databaseHelper: DatabaseHelper, empId:String): Int {
        val db = databaseHelper.writableDatabase

        val selection : String= GloboMedDbContract.EmployeeEntry.COLUMN_ID+" LIKE ?  "
        val selectionArgs: Array<String> = arrayOf(empId)

        return db.delete(GloboMedDbContract.EmployeeEntry.TABLE_NAME,selection,selectionArgs)
    }

    fun deleteAllEmployee(databaseHelper: DatabaseHelper): Int {
        val db = databaseHelper.writableDatabase
        return db.delete(GloboMedDbContract.EmployeeEntry.TABLE_NAME,"1",null)
    }
}