# recursive_resources — Recursive Resources quadaFork (MC 1.21.8 · Fabric · 클라 전용)

원작: `enjarai/recursive-resources`(enjarai · chylex · **MPL-2.0**) 태그 `2.7.2+1.21.7` 에서 포크 · 리모트 `upstream` = 원작 · `origin` = `kwangsoo-assemble/MCmod_RecursiveResources_quadaFork`
설계 정본: 하네스 `projects/mc_repositories/design/재귀_리소스_셰이더/plan.md` (M59) · 한글 README 원본: 하네스 `projects/mc_repositories/readme_ko/MCmod_RecursiveResources_quadaFork.md`

## 빌드

- JDK 21: `JAVA_HOME="C:\Program Files\Java\jdk-21" ./gradlew build` → `build/libs/recursive-resources-<판>+1.21.8.jar`
- 원작 빌드 도구 그대로(Loom `1.10-SNAPSHOT` · Gradle 8.13 · yarn 매핑) · 의존 `cicada-lib`(enjarai 메이븐) · 묶음 `shared-resources-api`

## GitHub · 판

- 작업본을 그대로 올린다 · 매듭마다 로컬 커밋 → 하네스 `repos.py publish recursive_resources`
- 포크 판 = 원작 판 + 넷째 자리(`2.7.2.1`) · jar 코드가 바뀌면 올리고 🎬 뒤 `repos.py release recursive_resources`
- ⚠ **MPL-2.0** — 고친 파일 · 새 파일은 같은 라이선스로 소스를 공개한다(공개 저장소) · 새 파일 머리에 MPL-2.0 표기
- 원작 최신을 받을 때: `git fetch upstream` → 판별 가지 · 태그를 보고 병합(1.21.8 판이 없으면 그대로 둔다)

## 포크가 바꾼 것

| 자리 | 무엇 |
|---|---|
| `gradle.properties` · `fabric.mod.json` | 1.21.8 · 판 · 포크 이름 · 저장소 주소 |
| `util/ResourcePackUtils.determinePackFolder` | 오버레이 팩은 본체(겹 목록 **끝**) 폴더 — 원작은 첫째(= 마지막 오버레이)를 써서 오버레이 팩이 폴더 보기에서 빠졌다 |
| `shader/ShaderpackTree` · `mixin/iris/*` · `recursiveresources.iris.mixins.json` · `mixin/RrMixinPlugin` | Iris 셰이더팩 하위 폴더(`shaderpacks/<묶음>/<팩>`) · Iris 없으면 빠진다 |
