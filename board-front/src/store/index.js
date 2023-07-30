import { createStore } from "vuex";
import http from "@/util/http-common";
export default createStore({
  state() {
    return {
      posts: [],
      post: {},
      comments: [],
      count: {},
    };
  },
  getters: {
    comments(state) {
      return state.comments;
    },
    posts(state) {
      return state.posts;
    },
    post(state) {
      return state.post;
    },
  },
  mutations: {
    COMMENTS(state, payload) {
      state.comments = payload.content;
    },
    POSTS(state, payload) {
      state.posts = payload.content;
      state.totalPages = payload.totalPages;
    },
    POST(state, payload) {
      state.post = payload.post;
    },
  },
  actions: {
    createPost(context, payload) {
      http
        .post("/post", payload.post)
        .then((response) => {
          payload.callback(response.status);
        })
        .catch((response) => {
          payload.callback(response.status);
        });
    },
    getPosts(context, page) {
      http.get("/post", { params: { page } }).then((response) => {
        context.commit("POSTS", response.data);
      });
    },
    getPost(context, payload) {
      http.get(`/post/${payload.postPk}`).then((response) => {
        context.commit("POST", { post: response.data });
      });
    },
    getComments(context, payload, para) {
      console.log(para);
      const page = payload.page;
      console.log(page)
      http.get(`post/${payload.postPk}/comment`, { params: { page } }).then((response) => {
        context.commit("COMMENTS", response.data);
      })
    }
  },

  modules: {
    // 모듈 정의
  },
});
