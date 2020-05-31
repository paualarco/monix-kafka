package monix.kafka.benchmarks

import monix.kafka.{KafkaProducerConfig, KafkaProducerSink}
import monix.execution.Scheduler.global
import monix.reactive.Observable
import org.apache.kafka.clients.producer.ProducerRecord

import scala.util.Random
trait KafkaFixture {

  val producerConf = KafkaProducerConfig.default.copy(
    bootstrapServers = List("127.0.0.1:9092")
  )

  val producer: KafkaProducerSink[String, String] = KafkaProducerSink[String,String](producerConf, global)

  val strToProducedRecord = {
    v: String =>
      new ProducerRecord[String, String]("topic_1P_1RF", Random.nextInt().toString, v)
  }

  }
