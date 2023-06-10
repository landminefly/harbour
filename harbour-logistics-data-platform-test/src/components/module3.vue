<template>
  <n-menu v-model:value="activeKey" mode="horizontal" :options="menuOptions" />
</template>

<script>
import { defineComponent, h, ref } from "vue";
import { NIcon } from "naive-ui";

function renderIcon(icon) {
  return () => h(NIcon, null, { default: () => h(icon) });
}

const menuOptions = [
  {
    label: () => h(
      "a",
      {
        href: "https://baike.baidu.com/item/%E4%B8%94%E5%90%AC%E9%A3%8E%E5%90%9F",
        target: "_blank",
        rel: "noopenner noreferrer"
      },
      "且听风吟"
    ),
    key: "hear-the-wind-sing",
    // icon: renderIcon(BookIcon)
  },
  {
    label: "1973年的弹珠玩具",
    key: "pinball-1973",
    // icon: renderIcon(BookIcon),
    disabled: true,
    children: [
      {
        label: "鼠",
        key: "rat"
      }
    ]
  },
  {
    label: "寻羊冒险记",
    key: "a-wild-sheep-chase",
    // icon: renderIcon(BookIcon),
    disabled: true
  },
  {
    label: "舞，舞，舞",
    key: "dance-dance-dance",
    // icon: renderIcon(BookIcon),
    children: [
      {
        type: "group",
        label: "人物",
        key: "people",
        children: [
          {
            label: "叙事者",
            key: "narrator",
            // icon: renderIcon(PersonIcon)
          },
          {
            label: "羊男",
            key: "sheep-man",
            // icon: renderIcon(PersonIcon)
          }
        ]
      },
      {
        label: "饮品",
        key: "beverage",
        // icon: renderIcon(WineIcon),
        children: [
          {
            label: "威士忌",
            key: "whisky"
          }
        ]
      },
      {
        label: "食物",
        key: "food",
        children: [
          {
            label: "三明治",
            key: "sandwich"
          }
        ]
      },
      {
        label: () => h(
          "div",
          {
            href: "javascript:alert(111);",
            // target: "_blank",
            rel: "noopenner noreferrer"
          },
          "111"
        ),
        key:"111",
      }
    ]
  }
];

export default defineComponent({
  setup() {
    return {
      activeKey: ref(null),
      menuOptions
    };
  }
});
</script>

<style>
#module3 {
  height: 100%;
  width: 100%;
  /* background-color:chartreuse; */
}
</style>