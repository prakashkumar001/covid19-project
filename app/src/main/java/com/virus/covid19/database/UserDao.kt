package com.virus.covid19.database

import androidx.room.*
import com.virus.covid19.database.entities.User


@Dao
interface UserDao {
        @Query("SELECT * FROM User ORDER BY ID")
        fun loadAllUser(): List<User?>?

        @Insert
        fun insertPerson(user: User?)

        @Update
        fun updatePerson(user: User?)

        @Delete
        fun delete(user: User?)

        @Query("SELECT * FROM User WHERE id = :id")
        fun loadUserById(id: Int): User?

        @Query("SELECT * FROM User WHERE email = :email")
        fun loadUserByEmail(email: String): User?

        @Query("SELECT * FROM User WHERE email = :email AND password = :password")
        fun getUserInfo(email: String,password: String): User?
}