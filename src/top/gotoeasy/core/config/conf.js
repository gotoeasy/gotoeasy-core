// -------------------------------------------------
/*
 * 定义函数接口供配置使用
 */
// 设定待扫描的目标包名
function scanPackages() {
	for (var i = 0; i < arguments.length; i++) {
		jsConfig.addScanPackage(arguments[i]);
	}
}
// 设定待初始化的Bean信息
function beans() {
	jsConfig.addBeans(arguments[0]);
}
//// 设定待初始化的简单值
//function keyValues() {
//	for (var i = 0; i < arguments.length; i++) {
//		jsConfig.addValues(arguments[i]);
//	}
//}
// 装载配置文件
function loadConfigFiles() {
	for (var i = 0; i < arguments.length; i++) {
		jsConfig.loadConfigFile(arguments[i]);
	}
}
// 定义类名匿名
function anonymousClass() {
	jsConfig.anonymousClass(arguments[0]);
}

// 定义简单键值属性
function properties() {
	jsConfig.addProperties(arguments[0]);
}

// -------------------------------------------------

// 设定默认类名匿名
anonymousClass({
	"Map" : "java.util.HashMap",
	"HashMap" : "java.util.HashMap",
	"List" : "java.util.ArrayList",
	"ArrayList" : "java.util.ArrayList",

	"String" : "java.lang.String",
	"string" : "java.lang.String",
	"Boolean" : "java.lang.Boolean",
	"boolean" : "java.lang.Boolean",
	"Integer" : "java.lang.Integer",
	"int" : "java.lang.Integer",
	"Long" : "java.lang.Long",
	"long" : "java.lang.Long",
	"Double" : "java.lang.Double",
	"double" : "java.lang.Double",
});
