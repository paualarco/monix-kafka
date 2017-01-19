/*
 * Copyright (c) 2014-2016 by its authors. Some rights reserved.
 * See the project homepage at: https://github.com/monixio/monix-kafka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package monix.kafka

import java.nio.ByteBuffer
import org.apache.kafka.common.serialization._
import org.apache.kafka.common.serialization.{Deserializer => KafkaDeserializer}
import org.apache.kafka.common.utils.Bytes
import language.existentials

/** Wraps a Kafka `Deserializer`, provided for
  * convenience, since it can be implicitly fetched
  * from the context.
  *
  * @param className is the full package path to the Kafka `Deserializer`
  *
  * @param classType is the actual [[Class]] for [[className]]
  *
  * @param constructor creates an instance of [[classType]].
  *        This is defaulted with a `Deserializer.Constructor[A]` function that creates a
  *        new instance using an assumed empty constructor.
  *        Supplying this parameter allows for manual provision of the `Deserializer`.
  */
final case class Deserializer[A](
  className: String,
  classType: Class[_ <: KafkaDeserializer[A]],
  constructor: Deserializer.Constructor[A] = (d: Deserializer[A]) => d.classType.newInstance()) {

  /** Creates a new instance. */
  def create(): KafkaDeserializer[A] =
    constructor(this)
}

object Deserializer {

  /** Alias for the function that provides an instance of
    * the Kafka `Deserializer`.
    */
  type Constructor[A] = (Deserializer[A]) => KafkaDeserializer[A]

  implicit val forStrings: Deserializer[String] =
    Deserializer(
      className = "org.apache.kafka.common.serialization.StringDeserializer",
      classType = classOf[StringDeserializer]
    )

  implicit val forByteArray: Deserializer[Array[Byte]] =
    Deserializer(
      className = "org.apache.kafka.common.serialization.ByteArrayDeserializer",
      classType = classOf[ByteArrayDeserializer]
    )

  implicit val forByteBuffer: Deserializer[ByteBuffer] =
    Deserializer(
      className = "org.apache.kafka.common.serialization.ByteBufferDeserializer",
      classType = classOf[ByteBufferDeserializer]
    )

  implicit val forBytes: Deserializer[Bytes] =
    Deserializer(
      className = "org.apache.kafka.common.serialization.BytesDeserializer",
      classType = classOf[BytesDeserializer]
    )

  implicit val forJavaDouble: Deserializer[java.lang.Double] =
    Deserializer(
      className = "org.apache.kafka.common.serialization.DoubleDeserializer",
      classType = classOf[DoubleDeserializer]
    )

  implicit val forJavaInteger: Deserializer[java.lang.Integer] =
    Deserializer(
      className = "org.apache.kafka.common.serialization.IntegerDeserializer",
      classType = classOf[IntegerDeserializer]
    )

  implicit val forJavaLong: Deserializer[java.lang.Long] =
    Deserializer(
      className = "org.apache.kafka.common.serialization.LongDeserializer",
      classType = classOf[LongDeserializer]
    )
}