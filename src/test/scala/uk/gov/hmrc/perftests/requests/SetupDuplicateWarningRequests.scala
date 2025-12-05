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


object SetupDuplicateWarningRequests extends ServicesConfiguration with RequestUtils {
  val navigateToCannotSetupDuplicate: HttpRequestBuilder =
    http("Navigate to cannot setup duplicate plan page")
      .get(s"$baseUrl$redirectUrl$cannotSetupNewPP")
      .check(status.is(200))
      .check(regex("You cannot set up a duplicate payment plan"))

  val navigateToExistingPPQpage: HttpRequestBuilder =
    http("Navigate to existing payment plan setup question page")
      .get(s"$baseUrl$redirectUrl$existingPP")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("You already have a payment plan with these details"))

  val submitExistingPaymentPlanDetails: HttpRequestBuilder =
    http("Select Yes for already have payment plan question")
      .post(s"$baseUrl$redirectUrl$existingPP")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", "true")
      .check(status.is(303))
      .check(header("Location").is("/direct-debits/direct-debit-payment-set-up"))

}
