/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.spark.ml.nlp

import scala.collection.mutable.ArrayBuffer

private[ml] class Path extends Serializable{
  var rnode: Node = new Node
  var lnode: Node = new Node
  var cost: Double = 0.0
  var fvector: Int = 0
  var fIdx: Integer = 0

  object Path{
    val path = new Path
    def getInstance: Path = {path}
  }
  def calExpectation(expected : ArrayBuffer[Double], Z: Double,
                     size: Integer,fCache: ArrayBuffer[Int]): Unit = {
    var c: Double = math.exp(lnode.alpha + cost + rnode.beta - Z)
    var idx: Int = 0
    // fIdx = fvector
    idx = fvector
    while(fCache(idx)!= -1) {
      expected(fCache(idx) + lnode.y*size + rnode.y) += c
      idx += 1
    }
  }
  def add(lnd : Node, rnd: Node): Unit = {
    lnode = lnd
    rnode = rnd
    lnode.rpath.append(this)
    rnode.lpath.append(this)
  }
  def clear(): Unit = {
    cost = 0
  }
}
