#pragma once

#include "sc-memory/sc_object.hpp"

#include "SetTheoryKeynodes.generated.hpp"

namespace setTheoryModule
{
class SetTheoryKeynodes : public ScObject
{
  SC_CLASS()
  SC_GENERATED_BODY()
public:
  SC_PROPERTY(Keynode("action_intersect_sets"), ForceCreate(ScType::NodeConstClass))
  static ScAddr action_intersect_sets;
};

}  // namespace setTheoryModule
