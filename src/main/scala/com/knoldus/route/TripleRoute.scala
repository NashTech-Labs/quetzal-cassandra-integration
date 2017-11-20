package com.knoldus.route

import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.PathDirectives._
import akka.http.scaladsl.server.directives.RouteDirectives._
import akka.http.scaladsl.server.directives.ParameterDirectives._
import com.google.inject.Inject
import com.knoldus.helper.QueryHelper
import com.knoldus.model.CassandraCluster
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.knoldus.service.{DirectPredicateHashing, Hashing, PredicateHashing, TripleOperations}


class TripleRoute @Inject()(tripleOperations: TripleOperations) {

  def getObjectForTriples: Route = {
    path("triples") {
      get {
        val response = tripleOperations.getTriplesAsJson("subject", "Property1")
        complete(HttpResponse(StatusCodes.OK,
          entity = HttpEntity(`application/json`, response)))
      }
    }
  }

}

object TripleRoute {
  def main(args: Array[String]): Unit = {
    val queryHelper = new QueryHelper
    val cassandraCluster = new CassandraCluster(queryHelper)
    val hashing = new Hashing
    val predicateHashing = new PredicateHashing(cassandraCluster, hashing, queryHelper)
    val directPredicateHashing = new DirectPredicateHashing(cassandraCluster, queryHelper)
    val tripleOperations = new TripleOperations()(cassandraCluster, predicateHashing, directPredicateHashing)
    val tripleRoute = new TripleRoute(tripleOperations)

    implicit val system = ActorSystem("Triples")
    implicit val executor = system.dispatcher
    implicit val materializer = ActorMaterializer()
    Http().bindAndHandle(tripleRoute.getObjectForTriples, "0.0.0.0", 8082)

  }
}
