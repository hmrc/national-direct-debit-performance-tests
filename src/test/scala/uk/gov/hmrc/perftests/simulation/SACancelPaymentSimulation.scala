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
import uk.gov.hmrc.perftests.requests.CancelPaymentPlanRequests.{navigateToCancelPPConfirmationPage, navigateToCancelPaymentPlanPage, selectYesToCancel}
import uk.gov.hmrc.perftests.requests.PaymentPlanRequests.{landOnSABudgetPPDetailsPage, redirectToSABudgetPPDetailsPage}
import uk.gov.hmrc.perftests.requests.SetupDDRequests.navigateToYourDDIPage

trait SACancelPaymentSimulation {
  this: PerformanceTestRunner =>

  setup("cancel-payment-plan-journey-sa-variablePPlan", "SA-Variable Payment Plan-Cancel PP Journey") withRequests
    (
      navigateToAuth,authLogIn(generateCredId("3c7")),
      navigateToYourDDIPage,
      redirectToSABudgetPPDetailsPage,
      landOnSABudgetPPDetailsPage,
      redirectToSABudgetPPRefPage,landOnSABudgetPPRefPage,
      navigateToCancelPaymentPlanPage,selectYesToCancel,
      navigateToCancelPPConfirmationPage
    )
}
