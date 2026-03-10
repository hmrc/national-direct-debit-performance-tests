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
import uk.gov.hmrc.perftests.requests.PaymentCYARequests.{navigateToDDDupeCYAPage, submitDDDetails}
import uk.gov.hmrc.perftests.requests.PaymentPlanRequests._
import uk.gov.hmrc.perftests.requests.PaymentReferenceRequests.{enterPaymentRefNumber, navigateToPaymentReferencePage}
import uk.gov.hmrc.perftests.requests.PaymentTypeRequests.{choosePaymentOption, navigateToPaymentOptionPage}
import uk.gov.hmrc.perftests.requests.SetupDDRequests.{mgdPaymentRef, navigateToYourDDIPage}

trait DuplicatePPSimulation {
  this: PerformanceTestRunner =>
  setup("duplicate-payment-plan-journey-DW1", "DW1-Duplicate Payment Plan Journey") withRequests
    (
      navigateToAuth, authLogIn("0000000009000204"),
      navigateToYourDDIPage,
      redirectToSABudgetPPDetailsPage, landOnSABudgetPPDetailsPage,
      redirectToSABudgetPPRefPage, landOnSABudgetPPRefPage,
      navigateToAmendPaymentPlanPage,
      navigateToAmendAmountPage,
      navigateToCheckAmendingDetailsPage, submitAmendPaymentPlanDetails,
      navigateToExistingPPQpage, submitExistingPPDetailS,
      navigateToPaymentPlanConfirmPage
    )
  setup("duplicate-payment-plan-journey-DW2", "DW2-Duplicate Payment Plan Journey") withRequests
    (
      navigateToAuth, authLogIn("0000000009000204"),
      navigateToYourDDIPage,
      redirectToSABudgetPPDetailsPage,
      landOnSABudgetPPDetailsPage,
      redirectToSetUpANewPPPage,
      navigateToPaymentOptionPage, choosePaymentOption("mgd"),
      navigateToPaymentPlanPage, choosePaymentPlan("variablePaymentPlan"),
      navigateToPaymentReferencePage, enterPaymentRefNumber(mgdPaymentRef),
      navigateToPaymentPlanStartDatePage, enterPaymentPlanStartDate,
      navigateToDDDupeCYAPage, submitDDDetails,
      navigateToDW2Page
    )
}
