#include "IntersectSetsManager.hpp"

namespace setTheoryModule
{
IntersectSetsManager::IntersectSetsManager(ScMemoryContext * context)
  : AgentManager(context)
{
}

ScAddrVector IntersectSetsManager::manage(ScAddrVector const & processParameters) const
{
  ScAddr const & set1 = processParameters[0];
  if (set1.IsValid() == SC_FALSE)
    SC_THROW_EXCEPTION(utils::ExceptionInvalidParams, "IntersectSetsManager: set1 is not valid");
  ScAddr const & set2 = processParameters[1];
  if (set2.IsValid() == SC_FALSE)
    SC_THROW_EXCEPTION(utils::ExceptionInvalidParams, "IntersectSetsManager: set2 is not valid");
  return {};
}
}  // namespace setTheoryModule
