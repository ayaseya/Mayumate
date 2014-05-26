package com.ayaseya.mayumate;

/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Helper class providing methods and constants common to other classes in the app.
 **/

/**
 *  ユーティリティクラスは、staticな共通の処理のメソッドを集めたクラスです。
 *　同じ処理が2箇所以上で出てきた場合は、ユーティリティクラスにまとめることができる可能性があります。
 *　ユーティリティクラスを使うと共通処理を1箇所にまとめることができますので、
 *  再利用性の向上ばかりでなく、あとから修正が発生してもユーティリティクラスの修正だけですみ、
 *  保守性が向上するというメリットがあります。
 **/

public final class CommonUtilities {

	/**
	 * Tag used on log messages.
	 **/
	static final String TAG = "Mayumate";

}
