## Apugli v1.8.0 (Fabric 1.19)
**Power Types**
- Added `apugli:action_on_target_death` and `apugli:redirect_lightning` power types.
- Added `horizontal_velocity` and `vertical_velocity` fields to `apugli:rocket_jump`
- Changed `velocity_clamp` field in `apugli:rocket_jump` to `velocity_clamp_multiplier`, also changed the logic of this.
- `apugli:rocket_jump`'s `cooldown` field is now defaulted to 1 for Apoli parity.

**Entity Condition Types**
- Added `apugli:hostile` entity condition type.

**Entity Action Types**
- Removed `apugli:drop_item`, it didn't even work lmao.