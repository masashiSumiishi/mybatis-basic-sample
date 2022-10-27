package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication
val mybatis: Mybatis = Mybatis()

fun main(args: Array<String>) {
	println("---- selectPrimaryKey--")
	mybatis.selectPrimaryKey()
	println("---- selectWhereName-------")
	mybatis.selectWhereName()
	println("---- selectWhereAge--------")
	mybatis.selectWhereAge()
	println("---- count--------")
	mybatis.countThanOrEqualTo25()
	println("---- countAllRows--------")
	mybatis.countAllRows()
	println("---- insertSingle--------")
	//mybatis.insertSingleRecord()
	println("---- insertMultiple--------")
	//mybatis.insertMultipleRecord()
	println("---- updateSingle--------")
	//mybatis.updateSingleRecord()
	println("---- updateWithNoRecordObject--------")
	//mybatis.updateNotRecordObject()
	println("---- updateWithRecordObject--------")
	//mybatis.updateValueNotPrimaryKeyWithRecordObject()
	println("---- deleteWithPrimaryKey--------")
	//mybatis.deleteWithPrimaryKey()
	println("---- deleteWithoutPrimaryKey--------")
	//mybatis.deleteWithoutPrimaryKey()
	runApplication<DemoApplication>(*args)
}
