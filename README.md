# MCmod_RecursiveResources_quadaFork

> A fork of [Recursive Resources](https://github.com/enjarai/recursive-resources) (enjarai · chylex) — built for Minecraft **1.21.8**, finds Iris **shader packs in sub-folders** too, and fixes overlay packs disappearing from the folder view.

> The changes in this fork were written with an AI coding tool (Claude Code) and verified in real use by the maintainer.
> The original has had no updates since 2025-07 (`2.7.2+1.21.7`) — this fork starts from the original tag `2.7.2+1.21.7` (original history kept).

<img src="img/example.png" width="650">

## At a glance

| Item | Value |
|---|---|
| Version | `2.7.2.1` (original `2.7.2` + fork revision 1) |
| Target | Minecraft 1.21.8 · Fabric · **client only** |
| Requires | Fabric Loader 0.19.3+ · Fabric API · [cicada-lib](https://modrinth.com/mod/cicada) 0.14.x |
| Works with | Iris (shader packs in sub-folders — without it only that feature is skipped) · Mod Menu |
| Replacing | Remove the original `recursive-resources-*.jar` and drop this jar in (same mod id `recursiveresources` · settings kept) |

## Original features (unchanged)

- Organize resource packs into folders and navigate them in game (sub-folders of `resourcepacks/` · updated live)
- Folder icons · ordering · pseudo-symlinks (`folder.json`) · enabling a whole folder at once
- Folder view · flat view · search by name and description (wildcards)

See the [original README](https://github.com/enjarai/recursive-resources#readme) for details.

## Added by this fork

### ① Shader packs in sub-folders (Iris)

Shader packs placed inside folders under `shaderpacks/` also show up in the Iris shader list and can be enabled.

```
shaderpacks/
  BSLClassic/                 ← shaders/ right inside → listed as «BSLClassic» (same name as plain Iris)
  RaD_Shaderpacks/            ← group folder · not listed itself · looked into
    weird_lab/                ← listed as «RaD_Shaderpacks/weird_lab»
    archive/old.zip           ← listed as «RaD_Shaderpacks/archive/old.zip»
```

- A pack is a folder with `shaders/` **right inside it**, or a `.zip` · any other folder is a group — searched up to 4 levels deep · it never descends into a pack
- Packs directly in `shaderpacks/` keep their name — the pack you have enabled and its settings keep working
- Iris saves the shader settings of a nested pack next to it (`RaD_Shaderpacks/weird_lab.txt`)
- Folders starting with a dot (`.git` …) are skipped · sorting matches Iris (case and formatting codes ignored)
- Without Iris only this feature is skipped and the game starts as usual

### ② Fixed — overlay packs missing from the folder view

Packs with `overlays` in their `pack.mcmeta` did not show in the folder view, only in the flat view.
The game wraps such a pack as a list of «overlays in reverse, then the base pack last»; the original used the first entry (= the last overlay folder) as the pack location and filed it under another folder. It now uses the base pack (the last entry).

## Build

```bash
# JDK 21
./gradlew build
# → build/libs/recursive-resources-2.7.2.1+1.21.8.jar
```

## Files changed or added by the fork

| File | What |
|---|---|
| `gradle.properties` · `fabric.mod.json` | 1.21.8 · version · fork name · repository links · `suggests: iris` |
| `util/ResourcePackUtils.java` | overlay pack location = base pack |
| `shader/ShaderpackTree.java` | builds the shader pack list (paths only · knows nothing about the game) |
| `mixin/iris/ShaderpackDirectoryManagerMixin.java` · `recursiveresources.iris.mixins.json` · `mixin/RrMixinPlugin.java` | hooks the Iris list function · skipped when Iris is absent |

## Credits · license

Original [enjarai/recursive-resources](https://github.com/enjarai/recursive-resources) (enjarai · chylex) — itself continuing [Resource Pack Organizer](https://github.com/chylex/Resource-Pack-Organizer) (chylex).
The shader feature hooks the list function of [Iris](https://github.com/IrisShaders/Iris) (LGPL-3.0), written by reading its public source (no Iris code was copied).

**MPL-2.0** (`LICENSE`) — same as the original. Changed and new files are MPL-2.0 too.

# 한국어

> [Recursive Resources](https://github.com/enjarai/recursive-resources)(enjarai · chylex)의 포크다 — Minecraft **1.21.8** 용으로 빌드하고, Iris **셰이더팩도 하위 폴더**에서 찾게 하고, 오버레이 팩이 폴더 보기에서 빠지는 버그를 고쳤다.

> 이 포크의 바뀐 부분은 AI 코딩 도구(Claude Code)로 작성했고 유지보수자가 실제 사용으로 확인했다.
> 원작은 2025-07(`2.7.2+1.21.7`) 이후 업데이트가 없다 — 이 포크는 원작 태그 `2.7.2+1.21.7` 에서 시작했다(원작 이력 그대로).

<img src="img/example.png" width="650">

## 한눈에

| 항목 | 값 |
|---|---|
| 버전 | `2.7.2.1`(원작 `2.7.2` + 포크 판 1) |
| 타깃 | Minecraft 1.21.8 · Fabric · **클라이언트 전용** |
| 의존 | Fabric Loader 0.19.3+ · Fabric API · [cicada-lib](https://modrinth.com/mod/cicada) 0.14.x |
| 함께 쓰면 | Iris(셰이더팩 하위 폴더 — 없으면 그 기능만 빠진다) · Mod Menu |
| 바꿔 끼우기 | 원작 `recursive-resources-*.jar` 를 빼고 이 jar 를 넣는다(모드 id `recursiveresources` 같음 · 설정 그대로) |

## 원작 기능 (그대로)

- 리소스팩을 폴더로 정리하고 게임 안에서 폴더를 드나들며 고른다(`resourcepacks/` 아래 하위 폴더 · 실시간 반영)
- 폴더 아이콘 · 순서 · 가상 링크(`folder.json`) · 폴더 통째로 켜기
- 폴더 보기 · 모두 펼친 보기 · 이름 · 설명 검색(와일드카드)

자세한 사용법은 [원작 README](https://github.com/enjarai/recursive-resources#readme).

## 포크가 더한 것

### ① 셰이더팩 하위 폴더 (Iris)

`shaderpacks/` 아래 폴더 안에 둔 셰이더팩도 Iris 셰이더 목록에 뜨고 켤 수 있다.

```
shaderpacks/
  BSLClassic/                 ← 바로 아래 shaders/ → 목록 «BSLClassic»(원래 Iris 와 같은 이름)
  RaD_Shaderpacks/            ← 묶음 폴더 · 목록에 안 뜸 · 안으로
    weird_lab/                ← 목록 «RaD_Shaderpacks/weird_lab»
    archive/old.zip           ← 목록 «RaD_Shaderpacks/archive/old.zip»
```

- 팩 = 폴더 **바로 아래** `shaders/` 가 있거나 `.zip` · 그 밖의 폴더는 묶음 — 안으로 최대 4단계 · 팩을 만나면 그 안으로는 안 내려간다
- `shaderpacks/` 바로 아래 팩은 이름이 원래 그대로 — 지금 켜 둔 팩 · 그 설정이 깨지지 않는다
- 하위 팩의 셰이더 설정은 Iris 가 팩 옆에 저장한다(`RaD_Shaderpacks/weird_lab.txt`)
- 점(`.`)으로 시작하는 폴더(`.git` 등)는 안 본다 · 정렬은 Iris 와 같다(대소문자 · 서식 문자 무시)
- Iris 가 없으면 이 기능만 빠지고 게임은 그대로 뜬다

### ② 고친 버그 — 오버레이 팩이 폴더 보기에서 빠짐

`pack.mcmeta` 에 `overlays` 가 있는 팩은 폴더 보기에서 보이지 않고 모두 펼친 보기에서만 보였다.
게임은 오버레이 팩을 «오버레이를 거꾸로 + 맨 끝에 본체» 목록으로 감싸는데, 원작은 그 목록의 첫째(= 마지막 오버레이 폴더)를 팩 자리로 써서 다른 폴더의 팩으로 보았다. 본체(목록 끝)를 쓰게 고쳤다.

## 빌드

```bash
# JDK 21
./gradlew build
# → build/libs/recursive-resources-2.7.2.1+1.21.8.jar
```

## 파일 (포크가 바꾸거나 더한 것)

| 파일 | 무엇 |
|---|---|
| `gradle.properties` · `fabric.mod.json` | 1.21.8 · 판 · 포크 이름 · 저장소 주소 · `suggests: iris` |
| `util/ResourcePackUtils.java` | 오버레이 팩 자리 = 본체 |
| `shader/ShaderpackTree.java` | 셰이더팩 목록 짜기(경로만 · 게임을 모른다) |
| `mixin/iris/ShaderpackDirectoryManagerMixin.java` · `recursiveresources.iris.mixins.json` · `mixin/RrMixinPlugin.java` | Iris 목록 함수에 끼우기 · Iris 없으면 건너뛰기 |

## 출처 · 라이선스

원작 [enjarai/recursive-resources](https://github.com/enjarai/recursive-resources)(enjarai · chylex) — [Resource Pack Organizer](https://github.com/chylex/Resource-Pack-Organizer)(chylex)를 이은 모드.
셰이더 기능은 [Iris](https://github.com/IrisShaders/Iris)(LGPL-3.0)의 공개 소스를 보고 그 목록 함수에 끼운다(Iris 코드는 옮겨 오지 않았다).

**MPL-2.0**(`LICENSE`) — 원작과 같다. 고친 파일 · 새 파일도 MPL-2.0 이다.
