package com.knoldus.route

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import com.google.inject.Inject
import com.knoldus.helper.QueryHelper
import com.knoldus.model.CassandraCluster
import com.knoldus.service.{DirectPredicateHashing, Hashing, PredicateHashing, TripleOperations}

import scala.concurrent.ExecutionContextExecutor


class TripleRoute @Inject()(tripleOperations: TripleOperations) extends Directives{

  def getObjectForTriples: Route = {
    path("triples") {
      get {
        parameter('subject.as[String], 'predicate.as[String]) { (subject, predicate) =>
          println(subject, predicate)
          val response = tripleOperations.getTriplesAsJson(subject, predicate)
          complete(HttpResponse(StatusCodes.OK,
            entity = HttpEntity(`application/json`, response)))
        }
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

    implicit val system: ActorSystem = ActorSystem("Triples")
    implicit val executor: ExecutionContextExecutor = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    Http().bindAndHandle(tripleRoute.getObjectForTriples, "0.0.0.0", 8082)

  }
}
