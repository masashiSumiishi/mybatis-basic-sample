package com.example.demo

import com.example.demo.database.*
import com.example.demo.database.UserDynamicSqlSupport.User.age
import com.example.demo.database.UserDynamicSqlSupport.User.id
import com.example.demo.database.UserDynamicSqlSupport.User.name
import com.example.demo.database.UserDynamicSqlSupport.User.profile
import org.apache.catalina.User
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.mybatis.dynamic.sql.SqlBuilder.*

class Mybatis {
  private fun createSessionFactory(): SqlSessionFactory {
    val resource = "mybatis-config.xml"
    val inputStream = Resources.getResourceAsStream(resource)
    return SqlSessionFactoryBuilder().build(inputStream)
  }

  fun selectPrimaryKey() {
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val user = mapper.selectByPrimaryKey(100)
      println(user)
    }
  }

  fun selectWhereName() {
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val userList = mapper.select {
        where(name, isEqualTo("Jiro"))
      }
      println(userList)
    }
  }

  fun selectWhereAge() {
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val userList = mapper.select {
        where(age, isGreaterThanOrEqualTo(25))
      }
      println(userList)
    }
  }

  fun countThanOrEqualTo25() {
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val count = mapper.count {
        where(age, isGreaterThanOrEqualTo(25))
      }
      println(count)
    }
  }

  fun countAllRows() {
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val count = mapper.count{ allRows() }
      println(count)
    }
  }

  fun insertSingleRecord() {
    val user = UserRecord(103, "Shiro", 18, "Hello")
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val count = mapper.insert(user)
      session.commit()
      println("${count}行のレコードを挿入しました")
    }
  }

  fun insertMultipleRecord() {
    val userList = listOf<UserRecord>(
      UserRecord(104, "Goro", 15, "Hello"),
      UserRecord(105, "Rokuro", 13, "Hello")
    )
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val count = mapper.insertMultiple(userList)
      session.commit()
      println("${count}行のレコードを挿入しました")
    }
  }

  fun updateSingleRecord() {
    val user = UserRecord(id = 105, profile = "Bye")
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val count = mapper.updateByPrimaryKeySelective(user)
      session.commit()
      println("${count}行のレコードを更新しました")
    }
  }

  fun updateValueNotPrimaryKeyWithNotRecordObject() {
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val count = mapper.update {
        set(profile).equalTo("Hey")
        where(id, isEqualTo(104))
      }
      session.commit()
      println("${count}行のレコードを更新しました")
    }
  }

  fun updateValueNotPrimaryKeyWithRecordObject() {
    val user = UserRecord(profile = "Good Morning")
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val count = mapper.update {
        updateSelectiveColumns(user)
        where(name, isEqualTo("Shiro"))
      }
      session.commit()
      println("${count}行のレコードを更新しました")
    }
  }

  fun deleteWithPrimaryKey() {
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val count = mapper.deleteByPrimaryKey(102)
      session.commit()
      println("${count}行のレコードを削除しました")
    }
  }

  fun deleteWithoutPrimaryKey() {
    createSessionFactory().openSession().use { session ->
      val mapper = session.getMapper(UserMapper::class.java)
      val count = mapper.delete {
        where(name, isEqualTo("jiro"))
      }
      session.commit()
      println("${count}行のレコードを削除しました")
    }
  }
}