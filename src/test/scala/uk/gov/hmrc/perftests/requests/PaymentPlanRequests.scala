/*
 * Copyright 2023 HM Revenue & Customs
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

object PaymentPlanRequests extends ServicesConfiguration with RequestUtils {

  val navigateToPaymentPlanPage: HttpRequestBuilder =
    http("Navigate to payment option page")
      .get(s"$baseUrl$redirectUrl$paymentPlan")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("What type of payment plan are you selecting?"))

  def choosePaymentPlan(paymentPlanTyoe: String): HttpRequestBuilder =
    http("Choose the payment Option")
      .post(s"$baseUrl$redirectUrl$paymentPlan")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", paymentPlanTyoe)
      .check(status.is(303))

  val redirectToSABudgetPPDetailsPage: HttpRequestBuilder =
    http("Redirect to payment plan details page")
      .get(s"$baseUrl$redirectUrl$saBudgetPaymentPlan")
      .formParam("directDebitReference", "990550021")
      .check(status.is(303))
//      .check(regex("Summary Payment plan"))

  val landOnSABudgetPPDetailsPage: HttpRequestBuilder =
    http("Land to payment plan details page")
      .get(s"$baseUrl$redirectUrl$paymentPlanSummaryPage")
      .check(status.is(200))

  val navigateToPaymentPlanStartDatePage: HttpRequestBuilder =
    http("Navigate to Payment plan start date page")
      .get(s"$baseUrl$redirectUrl$planStartDate")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("What date are you starting this payment plan?"))

  val ((startDay, startMonth, startYear), (endDay, endMonth, endYear)) = getStartAndEndDate

  val enterPaymentPlanStartDate: HttpRequestBuilder =
    http("Enter payment plan start date")
      .post(s"$baseUrl$redirectUrl$planStartDate")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value.day", startDay)
      .formParam("value.month", startMonth)
      .formParam("value.year", startYear)
      .check(status.is(303))

  val navigateToAddPaymentPlanEndDate: HttpRequestBuilder =
    http("Navigate to add payment plan end date page")
      .get(s"$baseUrl$redirectUrl$addPaymentPlanEndDateUrl")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Do you want to add a payment plan end date?"))

  val addPaymentPlanEndDate: HttpRequestBuilder =
    http("Add payment plan end date")
      .post(s"$baseUrl$redirectUrl$addPaymentPlanEndDateUrl")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", "true")
      .check(status.is(303))

  val navigateToBudgetPaymentPlanEndDatePage: HttpRequestBuilder =
    http("Navigate to budget payment plan end date page")
      .get(s"$baseUrl$redirectUrl$planEndDate")
      .check(status.is(200))
      .check(regex("When do you want this payment plan to end?"))

  val enterPaymentPlanEndDate: HttpRequestBuilder =
    http("Enter payment plan start date")
      .post(s"$baseUrl$redirectUrl$planEndDate")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value.day", endDay)
      .formParam("value.month", endMonth)
      .formParam("value.year", endYear)
      .check(status.is(303))
}
