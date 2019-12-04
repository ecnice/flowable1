module.exports = {
  extends: [require.resolve('@umijs/fabric/dist/eslint')],
  globals: {
    // ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION: true,
    page: true,
  },
  "rules":{
    "no-underscore-dangle": "off",
    "no-console": "off",
    "no-void": "off",
    "no-unused-vars": [1, {"vars": "all", "args": "after-used"}],// 不能有声明后未被使用的变量或参数
    "arrow-parens": 0,//箭头函数用小括号括起来
    "arrow-spacing": 0,//=>的前/后括号
    "no-mixed-requires": [0, false],//声明时不能混用声明类型
    "no-implicit-coercion": 1,//禁止隐式转换
    "no-implied-eval": 1,//禁止使用隐式eval
    "no-empty": 1,
    "no-extra-parens": 2,//禁止非必要的括号
    "block-no-empty": false,
    "no-empty-character-class": false, //禁止在正则表达式中使用空字符集


  }
};
