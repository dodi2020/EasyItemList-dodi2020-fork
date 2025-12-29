# Migration Plan: Minecraft 1.20.6 → 1.21.11

## Research Completed (with VPN access)

### Minecraft 1.21.11 Release Information
- **Release Date:** December 9, 2025
- **Official Name:** "Mounts of Mayhem" update
- **Protocol Version:** 774
- **Data Version:** 4671
- **Last version using 1.x.y format and requiring Java SE 21**

## Breaking Changes Analysis

### 1. Removal of Obfuscation (Major Change)
- Minecraft sources now use official mappings instead of obfuscated names
- Method and field names match vanilla official names
- Requires updating from intermediary mappings to official mappings
- **Impact:** Moderate - code review needed for any direct Minecraft class references

### 2. Resource Loader API Update
- Resource-loading system moved to Resource Loader v1
- Package structure changes (dropped "v1" from paths)
- Mixin/test structures reorganized
- **Impact:** Low - EasyItemList doesn't appear to heavily use resource loader internals

### 3. Data Generation and Tag Changes
- Tag definition and data generation for vanilla items updated
- Deprecated tags removed
- Tag names reordered for consistency
- **Impact:** Low - mod uses recipes but doesn't define custom tags

### 4. Registry/Tag Folders Depluralized
- `tags/blocks` → `tags/block`
- `tags/items` → `tags/item`
- **Impact:** Low - no custom tags in this mod

### 5. Event Hook Adjustments
- New hooks and events provided
- Legacy/deprecated mechanisms removed
- Event registration parameters may have changed
- **Impact:** Low - mod doesn't appear to use complex event systems

### 6. Fabric API Module Changes
- Some modules removed (fabric-models-v0)
- Deprecated Item API methods removed
- HudRenderCallback deprecated for HudLayerRegistrationCallback
- **Impact:** Low - mod uses minimal Fabric API surface

## Version Dependencies

### Milestone 1: 1.21.0 (Current - Already Updated)
```properties
minecraft_version=1.21
yarn_mappings=1.21+build.9
loader_version=0.15.11
emi_version=1.1.10+1.21+fabric
jei_version=1.21-fabric:19.20.1.91
rei_version=16.0.754
loom_version=1.6.14
```

### Milestone 2: 1.21.1
```properties
minecraft_version=1.21.1
yarn_mappings=1.21.1+build.3
loader_version=0.16.0
emi_version=1.1.22+1.21.1+fabric
jei_version=1.21.1-fabric:19.20.2.95
rei_version=16.0.763
loom_version=1.7-SNAPSHOT
```

### Milestone 3: 1.21.4
```properties
minecraft_version=1.21.4
yarn_mappings=1.21.4+build.1
loader_version=0.16.7
emi_version=1.1.18+1.21.4+fabric (use 1.21.1 version)
jei_version=1.21.4-fabric:20.1.0.68
rei_version=17.0.770
loom_version=1.8-SNAPSHOT
```

### Milestone 4: 1.21.5
```properties
minecraft_version=1.21.5
yarn_mappings=1.21.5+build.1
loader_version=0.16.9
emi_version=1.1.20+1.21.1+fabric (use 1.21.1 version)
jei_version=1.21.5-fabric:21.0.0.72
rei_version=18.0.778
loom_version=1.9-SNAPSHOT
```

### Milestone 5: 1.21.10
```properties
minecraft_version=1.21.10
yarn_mappings=1.21.10+build.2
loader_version=0.17.2
emi_version=1.1.22+1.21.1+fabric (use 1.21.1 version)
jei_version=1.21.10-fabric:26.0.0.80
rei_version=20.0.800
loom_version=1.10-SNAPSHOT
```

### Milestone 6: 1.21.11 (Final Target)
```properties
minecraft_version=1.21.11
yarn_mappings=1.21.11+build.3
loader_version=0.18.4
fabric_api_version=0.140.2+1.21.11
emi_version=1.1.22+1.21.1+fabric (EMI not yet updated for 1.21.11, use 1.21.1)
jei_version=1.21.11-fabric:27.3.0.12
rei_version=21.0.812 (REI not yet updated for 1.21.11, may work)
loom_version=1.14-SNAPSHOT
```

## Mod Dependency Compatibility Notes

### EMI (Exhaustively Many Items)
- **Latest Version:** 1.1.22 for Minecraft 1.21.1
- **1.21.11 Status:** No official release yet
- **Recommendation:** Use 1.21.1 version, should be compatible with minor internal changes
- **Risk:** Low - EMI typically works across minor versions

### JEI (Just Enough Items)
- **Latest Version:** 27.3.0.12 for Minecraft 1.21.11 Fabric
- **1.21.11 Status:** ✅ Official release available
- **Recommendation:** Use version 27.3.0.12
- **Risk:** None - fully supported

### REI (Roughly Enough Items)
- **Latest Version:** 16.0.754 for Minecraft 1.21.x
- **1.21.11 Status:** No explicit 1.21.11 release
- **Recommendation:** Use latest 1.21.10 or 1.21.1 version
- **Risk:** Low - REI typically updates rapidly

## Code Review Findings

### Current Codebase Analysis
✅ No usage of `new Identifier()` - already using `Identifier.of()`
✅ Recipe accessors compatible with 1.21 API
✅ Component system compatible with 1.21 API
✅ No custom resource loading that would be affected
✅ No custom tag definitions that need folder renaming
✅ Minimal event system usage
✅ No deprecated HudRenderCallback usage found

### Files Requiring Review
- `src/main/java/me/justahuman/easy_item_list/EasyItemList.java` - Main mod class
- `src/main/java/me/justahuman/easy_item_list/hooks/EmiHook.java` - EMI integration
- `src/main/java/me/justahuman/easy_item_list/hooks/JeiHook.java` - JEI integration
- `src/main/java/me/justahuman/easy_item_list/hooks/ReiHook.java` - REI integration
- `src/main/java/me/justahuman/easy_item_list/mixin/TrimRecipeAccessor.java` - Mixin accessor
- `src/main/java/me/justahuman/easy_item_list/mixin/TransformRecipeAccessor.java` - Mixin accessor

## Migration Strategy

### Phase 1: Update to 1.21.1 (Incremental)
1. Update gradle.properties with 1.21.1 versions
2. Update build.gradle for Loom 1.7
3. Attempt build and resolve any compilation errors
4. Test with EMI, JEI, and REI
5. Commit working version

### Phase 2: Update to 1.21.4 (Major Milestone)
1. Update gradle.properties with 1.21.4 versions
2. Update build.gradle for Loom 1.8
3. Test build and functionality
4. Commit working version

### Phase 3: Update to 1.21.5 (Major Milestone)
1. Update gradle.properties with 1.21.5 versions
2. Update build.gradle for Loom 1.9
3. Test build and functionality
4. Commit working version

### Phase 4: Update to 1.21.10 (Critical Milestone)
1. Update gradle.properties with 1.21.10 versions
2. Update build.gradle for Loom 1.10
3. Test build and functionality
4. Perform thorough integration testing
5. Commit working version

### Phase 5: Update to 1.21.11 (Final Target)
1. Update gradle.properties with 1.21.11 versions
2. Update build.gradle for Loom 1.14
3. Add Fabric API dependency (0.140.2+1.21.11)
4. Handle any EMI/REI version compatibility issues
5. Test build and functionality
6. Perform comprehensive integration testing
7. Final validation and commit

## Build Testing Approach

For each milestone:
1. Clean build: `./gradlew clean`
2. Build mod: `./gradlew build`
3. Check for compilation errors
4. If errors occur:
   - Review error messages
   - Check API changes in Fabric/Minecraft
   - Update code as needed
   - Retry build
5. If build succeeds, proceed to next milestone

## Known Risks

### High Priority
- ❌ **Network Access:** maven.fabricmc.net must be accessible for builds
  - **Mitigation:** VPN enabled (as per user request)
  
### Medium Priority
- ⚠️ **EMI Compatibility:** No official 1.21.11 release yet
  - **Mitigation:** 1.21.1 version should work, fallback to JEI/REI only if needed
  
- ⚠️ **REI Compatibility:** No explicit 1.21.11 release
  - **Mitigation:** Use latest available version, should be compatible

### Low Priority
- ⚠️ **Mapping Changes:** Official mappings vs intermediary
  - **Mitigation:** Yarn mappings handle this automatically
  
- ⚠️ **API Deprecations:** Some Fabric API methods may be deprecated
  - **Mitigation:** Compiler warnings will indicate needed changes

## Success Criteria

1. ✅ Mod compiles without errors on 1.21.11
2. ✅ Mod loads in Minecraft 1.21.11 with Fabric
3. ✅ Integration works with at least JEI (confirmed available)
4. ✅ Integration works with EMI (if compatible)
5. ✅ Integration works with REI (if compatible)
6. ✅ Server-side recipes are correctly exposed to recipe viewers
7. ✅ NBT items display correctly in recipe lists

## Next Steps

1. ✅ Complete research (done with VPN access)
2. ⬜ Update configuration to 1.21.11
3. ⬜ Attempt build with 1.21.11 dependencies
4. ⬜ Resolve any compilation errors
5. ⬜ Test with each recipe viewer mod
6. ⬜ Validate functionality
7. ⬜ Document any issues or workarounds

## References

- Fabric Documentation: https://docs.fabricmc.net/
- Fabric API Changelog: https://modrinth.com/mod/fabric-api/changelog
- Minecraft 1.21.11 Release: https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-11
- EMI Mod: https://modrinth.com/mod/emi
- JEI Mod: https://www.curseforge.com/minecraft/mc-mods/jei
- REI Mod: https://www.curseforge.com/minecraft/mc-mods/roughly-enough-items
