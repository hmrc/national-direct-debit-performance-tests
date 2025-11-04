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

object AmendPaymentPlanRequests extends ServicesConfiguration with RequestUtils {

  val navigateToAmendPaymentPlanPage: HttpRequestBuilder =
    http("Navigate to payment plan details page")
      .get(s"$baseUrl$redirectUrl$amendPaymentPlanPage")
      .check(status.is(200))
      .check(regex("Amending this payment plan"))

  val navigateToAmendAmountPage: HttpRequestBuilder =
    http("Navigate to amend amount page")
      .get(s"$baseUrl$redirectUrl$amountToBePaid")
      .check(saveCsrfToken())
      .check(status.is(200))

  val enterAmendPaymentAmount: HttpRequestBuilder =
    http("Enter the amount to be paid")
      .post(s"$baseUrl$redirectUrl$amountToBePaid")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", 100)
      .check(status.is(303))

  val navigateToPaymentPlanEndDatePage: HttpRequestBuilder =
    http("Navigate to payment plan end date page")
      .get(s"$baseUrl$redirectUrl$paymentPlanEndDate")
      .check(status.is(200))
      .check(regex("What date are you ending this payment plan?"))

  val (day, month, year) = getRandomDateWithin30Days

  val submitPaymentPlanEndDate: HttpRequestBuilder =
    http("Enter payment plan end date")
      .post(s"$baseUrl$redirectUrl$paymentPlanEndDate")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value.day", day)
      .formParam("value.month", month)
      .formParam("value.year", year)
      .check(status.is(303))

  val navigateToPaymentPlanCYAPage: HttpRequestBuilder =
    http("Navigate to payment plan end date page")
      .get(s"$baseUrl$redirectUrl$paymentPlanCYAPage")
      .check(saveCsrfToken())
      .check(status.is(200))
      .check(regex("Confirm new payment plan details"))

  val navigateToExistingPaymentPlanCYAPage: HttpRequestBuilder =
    http("Navigate to payment plan end date page")
      .get(s"$baseUrl$redirectUrl$confirmExistingPayment")
      .check(status.is(200))
      .check(regex("Confirm new payment plan details"))

  val navigateToAmendAmountToBePaidPage: HttpRequestBuilder =
    http("Navigate to amend payment plan end date page")
      .get(s"$baseUrl$redirectUrl$amendAmountToBePaid")
      .check(status.is(200))
      .check(regex("What amount do you need to pay?"))

  val navigateToAmendAEndDatePage: HttpRequestBuilder =
    http("Navigate to amend payment plan end date page")
      .get(s"$baseUrl$redirectUrl$amendEndDate")
      .check(status.is(200))
      .check(regex("What date are you ending this payment plan?"))

  val submitPaymentPlanDetails: HttpRequestBuilder =
    http("Enter payment plan details")
      .post(s"$baseUrl$redirectUrl$paymentPlanCYAPage")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))

  val navigateToPaymentPlanConfirmPage: HttpRequestBuilder =
    http("Navigate to payment plan confirmation page")
      .get(s"$baseUrl$redirectUrl$ppConfirmationPage")
      .check(status.is(200))
      .check(regex("Payment plan amended"))

  val redirectToSABudgetPPRefPage: HttpRequestBuilder =
    http("Select SA Budget payment plan details page")
      .get(s"$baseUrl$redirectUrl$saBudgetPaymentPlanRef")
      .formParam("paymentPlanReference", "200000802")
      .check(status.is(303))

  val landOnSABudgetPPRefPage: HttpRequestBuilder =
    http("Land on SA Budget Payment plan page")
      .get(s"$baseUrl$redirectUrl$paymentPlanDetailsPage")
      .check(status.is(200))

  val navigateToExistingPPQpage: HttpRequestBuilder =
    http("Navigate to existing payment plan setup question page")
      .get(s"$baseUrl$redirectUrl$existingPP")
      .check(status.is(200))

  val submitExistingPPDetailS: HttpRequestBuilder =
    http("Select Yes for already have payment plan question")
      .post(s"$baseUrl$redirectUrl$existingPP")
      .formParam("csrfToken", "#{csrfToken}")
      .formParam("value", "true")
      .check(status.is(303))
      .check(header("Location").is("/direct-debits/confirm-new-payment-plan-details"))

  val submitExistingPaymentPlanDetails: HttpRequestBuilder =
    http("Submit existing payment plan details")
      .post(s"$baseUrl$redirectUrl$confirmExistingPayment")
      .formParam("csrfToken", "#{csrfToken}")
      .check(status.is(303))




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

}
