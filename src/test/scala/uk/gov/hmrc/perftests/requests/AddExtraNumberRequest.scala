/*
 * Copyright 2025 HM Revenue & Customs
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

package uk.gov.hmrc.perftests.requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object AddExtraNumberRequest extends ServicesConfiguration with RequestUtils {

  val navigateToAddExtraNumbersPage: HttpRequestBuilder =
    http("Tell us about this payment")
      .get(s"$baseUrl$redirectUrl$addExtraNumbersOption")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Tell us about this payment"))

  def chooseAddExtraNumber(addExtraNumber: String): HttpRequestBuilder =
    http("Add 4 extra numbers to your payment reference")
      .post(s"$baseUrl$redirectUrl$addExtraNumbersOption")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", addExtraNumber)
      .check(status.is(303))
}
