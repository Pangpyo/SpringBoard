import { createRouter, createWebHistory } from "vue-router";


const routes = [
  {
    path: "/",
    name: "HomeView",
    component: () => import("@/views/HomeView"),
    children :[
      {
        path: "",
        name: "PostList",
        component: () => import("@/components/PostList"),
      },
      {
        path: "detail/:postPk",
        name: "PostDetail",
        component: () => import("@/components/PostDetail"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;