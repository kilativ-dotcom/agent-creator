#include "sc-memory/kpm/sc_agent.hpp"
#include "sc_test.hpp"
#include "scs_loader.hpp"

#include "agent/IntersectSetsAgent.hpp"

#include "keynodes/SetTheoryKeynodes.hpp"

namespace testIntersectSetsAgent
{
ScsLoader loader;

using TestIntersectSetsAgent = ScMemoryTest;

void initialize()
{
  scAgentsCommon::CoreKeynodes::InitGlobal();
  setTheoryModule::SetTheoryKeynodes::InitGlobal();

  ScAgentInit(true);
  SC_AGENT_REGISTER(setTheoryModule::IntersectSetsAgent)
}

void shutdown()
{
  SC_AGENT_UNREGISTER(setTheoryModule::IntersectSetsAgent)
}

TEST_F(TestIntersectSetsAgent, successfulAction)
{
  initialize();
  shutdown();
}
}  // namespace testIntersectSetsAgent
