#pragma once

#include <unordered_map>

template<typename K, typename V>
const V* tryGetFromMap(const std::unordered_map<K, const V&>& map, const K& key) {
	auto iter = map.find(key);
	if (iter == map.end()) {
		return nullptr;
	}
	return &iter->second;
}

//#include <vector>
//
//template<typename K, typename V>
//struct KeyAccessor {
//	static const K& getKey(const V& value) { return value.key(); }
//};
//
//
//template<typename K, typename V>
//class KeyedCollection {
//
//};
