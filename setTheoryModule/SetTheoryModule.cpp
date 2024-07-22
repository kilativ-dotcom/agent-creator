#include "agent/IntersectSetsAgent.hpp"

#include "keynodes/SetTheoryKeynodes.hpp"

#include "utils/ActionUtils.hpp"

#include "SetTheoryModule.hpp"

namespace setTheoryModule
{
SC_IMPLEMENT_MODULE(SetTheoryModule)

sc_result SetTheoryModule::InitializeImpl()
{
  if (!SetTheoryKeynodes::InitGlobal())
  {
    return SC_RESULT_ERROR;
  }

  ScMemoryContext context;
  if (ActionUtils::isActionDeactivated(&context, SetTheoryKeynodes::action_intersect_sets))
  {
    SC_LOG_WARNING("action_intersect_sets is deactivated");
  }
  else
  {
    SC_AGENT_REGISTER(IntersectSetsAgent)
  }

  return SC_RESULT_OK;
}

sc_result SetTheoryModule::ShutdownImpl()
{
  SC_AGENT_UNREGISTER(IntersectSetsAgent)

  return SC_RESULT_OK;
}
}  // namespace setTheoryModule
