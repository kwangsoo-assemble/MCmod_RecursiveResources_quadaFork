/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * quadaFork addition — shader packs in sub-folders of shaderpacks/.
 */
package nl.enjarai.recursiveresources.mixin.iris;

import java.nio.file.Path;
import java.util.List;
import nl.enjarai.recursiveresources.RecursiveResources;
import nl.enjarai.recursiveresources.shader.ShaderpackTree;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Iris 셰이더 목록 — 바로 아래만 보던 것을 하위 폴더 팩까지({@link ShaderpackTree}).
 * 불러오기 · 설정 저장은 Iris 그대로(이름 = shaderpacks 기준 경로).
 */
@Pseudo
@Mixin(targets = "net.irisshaders.iris.shaderpack.discovery.ShaderpackDirectoryManager", remap = false)
public abstract class ShaderpackDirectoryManagerMixin {
	@Shadow(remap = false)
	@Final
	private Path root;

	@Inject(method = "enumerate", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
	private void recursiveresources$nestedPacks(CallbackInfoReturnable<List<String>> cir) {
		try {
			cir.setReturnValue(ShaderpackTree.list(this.root));
		} catch (Exception e) {
			// ⚠ 실패하면 Iris 원래 목록으로(바로 아래만) — 셰이더 화면이 비지 않게
			RecursiveResources.LOGGER.warn("Failed to list nested shader packs, falling back to Iris", e);
		}
	}
}
