/***
 * Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/

package com.test.signature;

import java.util.ArrayList;
import java.util.HashMap;

public class TypeContext {
	private HashMap<String, ArrayList<String>> _known_types;

	public TypeContext() {
		_known_types = new HashMap<String, ArrayList<String>>();
	}

	public void register(String type_name, String package_name) {
		ArrayList<String> known_full_types = _known_types.get(type_name);

		if (known_full_types == null) {
			known_full_types = new ArrayList<String>();
			_known_types.put(type_name, known_full_types);
		}

		known_full_types.add(package_name);
	}

	public String[][] resolveType(String type_name) {
		ArrayList<String> known_full_types = _known_types.get(type_name);
		if (known_full_types == null) {
			return null;
		}

		String[][] result = new String[known_full_types.size()][2];

		for (int ix = 0; ix < known_full_types.size(); ix++) {
			result[ix][0] = known_full_types.get(ix);
			result[ix][1] = type_name;
		}

		return result;
	}
}
