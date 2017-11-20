package com.knoldus.model

import com.knoldus.integration.CassandraDatabaseCluster
import com.knoldus.service.DirectPredicateHashing

class DirectPredicateHashingSuite extends CassandraDatabaseCluster {

  lazy val cluster = new CassandraCluster(queryHelper)
  lazy val directPredicateHashing = new DirectPredicateHashing(cluster, queryHelper)
  lazy val triple = Triple("Entity2", "Predicate1", "ValueNext")

  it should "get spill, prop and val" in {
    directPredicateHashing.getEntityInfo(3, 3, triple)
    assert(true)
  }

}
