
## Hiero

LibGDX - HIERO (font editor)

### create a new font

* run gradle task gdxHieroRun
* import your font (TTF, OTF, ...etc)
* fix font absolute path by relative path from gradle project using it.
* save as hiero file

### edit the font

* create task for your asset : gdxHieroEdit("assets-src/skins/hud-skin/default.hiero")
* call gradle task named : gdxHieroEdit_assets-src/skins/hud-skin/default.hiero

### generate bitmap font

* create task for your asset : gdxHieroExport("assets-src/skins/hud-skin/default.hiero", "$buildDir/skins/hud-skin")
* run gradle task : gdxHieroExport_assets-src/skins/hud-skin/default.hiero

Note that gdxHieroEdit_xxx task is automatically added with gdxHieroExport_xxx
