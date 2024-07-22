#pragma once

#include "manager/AgentManager.hpp"

namespace setTheoryModule
{
class IntersectSetsManager : public commonModule::AgentManager
{
public:
  explicit IntersectSetsManager(ScMemoryContext * context);

  ScAddrVector manage(ScAddrVector const & processParameters) const override;
};

}  // namespace setTheoryModule
