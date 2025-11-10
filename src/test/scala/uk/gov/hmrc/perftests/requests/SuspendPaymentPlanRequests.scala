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

object SuspendPaymentPlanRequests extends ServicesConfiguration with RequestUtils {

  val navigateToSuspendPaymentPlanPage: HttpRequestBuilder =
    http("Navigate to suspend payment plan page")
      .get(s"$baseUrl$redirectUrl$suspendPaymentPlanPage")
      .check(status.is(200))
      .check(regex("Suspending this payment plan"))

  val navigateToSuspendPeriodPage: HttpRequestBuilder =
    http("Navigate to suspend period page")
      .get(s"$baseUrl$redirectUrl$suspendPeriodPage")
      .check(status.is(200))
      .check(saveCsrfToken())
      .check(regex("Enter suspension dates"))

  val ((startDay, startMonth, startYear), (endDay, endMonth, endYear)) = getStartAndEndDate

  val submitSuspendPeriodDetails: HttpRequestBuilder =
    http("Enter suspend payment plan start and end dates")
      .post(s"$baseUrl$redirectUrl$suspendPeriodPage")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("suspensionPeriodRangeStartDate.day", startDay)
      .formParam("suspensionPeriodRangeStartDate.month", startMonth)
      .formParam("suspensionPeriodRangeStartDate.year", startYear)
      .formParam("suspensionPeriodRangeEndDate.day", endDay)
      .formParam("suspensionPeriodRangeEndDate.month", endMonth)
      .formParam("suspensionPeriodRangeEndDate.year", endYear)
      .check(status.is(303))

  val navigateToCheckSuspendPeriodPage: HttpRequestBuilder =
    http("Navigate to check suspend period page")
      .get(s"$baseUrl$redirectUrl$checkSuspendPeriod")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Check your suspension details"))

  val confirmSuspendPeriod: HttpRequestBuilder =
    http("Submit suspend period details")
      .post(s"$baseUrl$redirectUrl$checkSuspendPeriod")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  val navigateToSuspendPPConfirmationPage: HttpRequestBuilder =
    http("Navigate to suspend payment plan confirmation page")
      .get(s"$baseUrl$redirectUrl$suspendConfirmPage")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(200))
      .check(regex("Payment plan suspended"))

  val navigateToChangeSuspendPeriodPage: HttpRequestBuilder =
    http("Navigate to change suspend payment plan dates page")
      .get(s"$baseUrl$redirectUrl$suspendPeriodPage")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Enter suspension dates"))

  val navigateToRemoveSuspensionPage: HttpRequestBuilder =
    http("Navigate to remove suspension page")
      .get(s"$baseUrl$redirectUrl$removeSuspension")
      .check(saveCsrfToken())
      .check(status.in(200,303))
      .check(regex("Removing this suspension"))

  val confirmRemoveSuspension: HttpRequestBuilder =
    http("Select Yes to remove suspension")
      .post(s"$baseUrl$redirectUrl$removeSuspension")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", "true")
      .check(status.is(303))

  val navigateToRemoveSuspensionConfirmPage: HttpRequestBuilder =
    http("Navigate to suspend payment plan confirmation page")
      .get(s"$baseUrl$redirectUrl$removeSuspensionConfirm")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(200))
      .check(regex("Payment plan suspension removed"))

}
