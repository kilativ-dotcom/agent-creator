#include "sc-agents-common/utils/AgentUtils.hpp"
#include "sc-agents-common/utils/IteratorUtils.hpp"

#include "keynodes/SetTheoryKeynodes.hpp"

#include "IntersectSetsAgent.hpp"

namespace setTheoryModule
{
SC_AGENT_IMPLEMENTATION(IntersectSetsAgent)
{
  ScAddr const & actionNode = otherAddr;
  try
  {
    if (!checkActionClass(actionNode))
      return SC_RESULT_OK;
    SC_LOG_INFO("IntersectSetsAgent started");

    ScAddr const & set1 =
        utils::IteratorUtils::getAnyByOutRelation(&m_memoryCtx, actionNode, scAgentsCommon::CoreKeynodes::rrel_1);
    if (set1.IsValid() == SC_FALSE)
      SC_THROW_EXCEPTION(utils::ExceptionInvalidParams, "IntersectSetsAgent: set1 is not valid");

    ScAddr const & set2 =
        utils::IteratorUtils::getAnyByOutRelation(&m_memoryCtx, actionNode, scAgentsCommon::CoreKeynodes::rrel_2);
    if (set2.IsValid() == SC_FALSE)
      SC_THROW_EXCEPTION(utils::ExceptionInvalidParams, "IntersectSetsAgent: set2 is not valid");

    utils::AgentUtils::finishAgentWork(&m_memoryCtx, actionNode, answerElements, true);
    SC_LOG_INFO("IntersectSetsAgent finished");
    return SC_RESULT_OK;
  }
  catch (utils::ScException const & exception)
  {
    SC_LOG_ERROR(exception.Description());
    utils::AgentUtils::finishAgentWork(&m_memoryCtx, actionNode, false);
    SC_LOG_INFO("IntersectSetsAgent finished with error");
    return SC_RESULT_ERROR;
  }
}

bool IntersectSetsAgent::checkActionClass(ScAddr const & actionNode)
{
  return m_memoryCtx.HelperCheckEdge(
      SetTheoryKeynodes::action_intersect_sets, actionNode, ScType::EdgeAccessConstPosPerm);
}

}  // namespace setTheoryModule
