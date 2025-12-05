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

package uk.gov.hmrc.perftests.simulation

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.requests.AmendPaymentPlanRequests._
import uk.gov.hmrc.perftests.requests.AuthLoginRequests.{authLogIn, navigateToAuth}
import uk.gov.hmrc.perftests.requests.PaymentAmountRequests.{enterRegularPaymentAmount, navigateToRegularPaymentAmountPage}
import uk.gov.hmrc.perftests.requests.PaymentCYARequests.{navigateToDDCYAPage, submitDDDetails}
import uk.gov.hmrc.perftests.requests.PaymentConfirmationRequests.navigateToDDConfirmationPage
import uk.gov.hmrc.perftests.requests.PaymentPlanRequests.{addPaymentPlanEndDate, choosePaymentPlan, enterPaymentPlanEndDate, enterPaymentPlanStartDate, landOnSABudgetPPDetailsPage, navigateToAddPaymentPlanEndDate, navigateToBudgetPaymentPlanEndDatePage, navigateToPaymentPlanPage, navigateToPaymentPlanStartDatePage, redirectToSABudgetPPDetailsPage}
import uk.gov.hmrc.perftests.requests.PaymentReferenceRequests.{enterPaymentRefNumber, navigateToPaymentReferencePage}
import uk.gov.hmrc.perftests.requests.PaymentTypeRequests.choosePaymentOption
import uk.gov.hmrc.perftests.requests.SelectPaymentFrequencyRequests.{navigateToPaymentFrequencyPage, selectFrequency}
import uk.gov.hmrc.perftests.requests.SetupDDRequests.{navigateToYourDDIPage, saPaymentRef}

trait PAYEDW1AmendPaymentSimulation {
  this: PerformanceTestRunner =>
  setup("amend-payment-plan-dw1-journey-sa-budgetPPlan", "SA-Budget Payment Plan- DW1 Amend PP Journey") withRequests
    (
      navigateToAuth,authLogIn("0000000009000205"),
      navigateToYourDDIPage,
      redirectToSABudgetPPDetailsPage,
      landOnSABudgetPPDetailsPage,
      choosePaymentOption("sa"),
      choosePaymentOption("sa"),
      navigateToPaymentPlanPage, choosePaymentPlan("budgetPaymentPlan"),
      navigateToPaymentReferencePage, enterPaymentRefNumber(saPaymentRef),
      navigateToPaymentFrequencyPage, selectFrequency("monthly"),
      navigateToRegularPaymentAmountPage, enterRegularPaymentAmount,
      navigateToPaymentPlanStartDatePage, enterPaymentPlanStartDate,
      navigateToAddPaymentPlanEndDate, addPaymentPlanEndDate,
      navigateToBudgetPaymentPlanEndDatePage,enterPaymentPlanEndDate,
      navigateToDDCYAPage, submitDDDetails,
      navigateToExistingPPQpage,submitExistingPPDetailS,
      navigateToDDConfirmationPage
    )
}
