package com.knoldus.model

import com.knoldus.cassandra.CassandraDatabaseCluster
import com.knoldus.service.DirectPredicateHashing

class DirectPredicateHashingSuite extends CassandraDatabaseCluster {

  lazy val directPredicateHashing = new DirectPredicateHashing(cluster, queryHelper)
  lazy val triple = Triple("Entity2", "Predicate1", "ValueNext")

  it should "get spill, prop and val" in {
    val data = directPredicateHashing.getEntityInfo(3, 3, triple)
    println(data)
    assert(true)
  }

}
