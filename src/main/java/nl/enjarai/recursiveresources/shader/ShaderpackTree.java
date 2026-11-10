/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * quadaFork addition — shader packs in sub-folders of shaderpacks/.
 */
package nl.enjarai.recursiveresources.shader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 셰이더팩 목록(Minecraft · Iris 를 모른다 — 경로만).
 * 팩 = 바로 아래 {@code shaders/} 가 있는 폴더 또는 {@code .zip} · 묶음 = 그 밖의 폴더(최대 {@link #MAX_DEPTH} 단계 · 팩에서 멈춤).
 * 바로 아래 팩은 이름만(Iris 원래와 같음) · 하위 팩은 {@code 묶음/…/팩}({@code /} 구분) — Iris 는 이 이름을 {@code shaderpacks.resolve(이름)} 으로 불러오고
 * 설정을 {@code 이름 + ".txt"}(팩 옆)에 저장한다.
 */
public final class ShaderpackTree {
	/** 묶음 폴더를 내려가는 최대 단계(바로 아래 = 1). */
	public static final int MAX_DEPTH = 4;

	private static final Comparator<String> ORDER = Comparator.comparing(ShaderpackTree::removeFormatting, String.CASE_INSENSITIVE_ORDER)
			.thenComparing(ShaderpackTree::removeFormatting, Comparator.naturalOrder());

	private ShaderpackTree() {
	}

	/** shaderpacks 폴더 아래 팩 이름 전부 — 정렬(대소문자 · 서식 문자 무시 · 경로 전체). */
	public static List<String> list(Path root) throws IOException {
		List<String> out = new ArrayList<>();
		walk(root, "", 1, out);
		out.sort(ORDER);
		return out;
	}

	/** 팩인가 — 바로 아래 {@code shaders/} 가 있는 폴더 또는 zip 파일. */
	public static boolean isPack(Path p) {
		if (Files.isDirectory(p)) {
			return Files.isDirectory(p.resolve("shaders"));
		}
		return Files.isRegularFile(p) && p.getFileName().toString().toLowerCase(java.util.Locale.ROOT).endsWith(".zip");
	}

	private static void walk(Path dir, String prefix, int depth, List<String> out) throws IOException {
		List<Path> children;
		try (Stream<Path> s = Files.list(dir)) {
			children = s.toList();
		}
		for (Path c : children) {
			String name = c.getFileName().toString();
			if (isPack(c)) {
				out.add(prefix + name);
			} else if (Files.isDirectory(c) && !name.startsWith(".") && depth < MAX_DEPTH) {
				walk(c, prefix + name + "/", depth + 1, out);
			}
		}
	}

	/** Iris 와 같은 서식 문자 지우기 — {@code §x} 두 글자. */
	static String removeFormatting(String formatted) {
		StringBuilder b = new StringBuilder(formatted.length());
		for (int i = 0; i < formatted.length(); i++) {
			char ch = formatted.charAt(i);
			if (ch == '§') {
				i++;
				continue;
			}
			b.append(ch);
		}
		return b.toString();
	}
}
