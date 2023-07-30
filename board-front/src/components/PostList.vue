<template>
  <div>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">글번호</th>
          <th scope="col">제목</th>
          <th scope="col">작성자</th>
          <th scope="col">댓글수</th>
          <th scope="col">좋아요</th>
          <th scope="col">조회수</th>
          <th scope="col">작성일</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="post in this.posts" :key="post.postPd">
          <th scope="row">{{ post.postPk }}</th>
          <td style="cursor: pointer;" @click="rowClickListener(post.postPk)"><span v-if="post.new" class="text-primary">new </span>{{ post.title }}</td>
          <td>{{ post.postAuthor }}</td>
          <td>{{ post.commentCount }}</td>
          <td>{{ post.postLike }}</td>
          <td>{{ post.hit }}</td>
          <td>{{ post.createdAt }}</td>
        </tr>
      </tbody>
    </table>
    <div>
      <input type="number" v-model.number="nowPage" min="1" />
      <button @click="goToInputPage">Go</button>
    </div>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
export default {
  data: function () {
    return {
      nowPage: 1,
    };
  },
  methods: {
    ...mapActions(["getPosts"]),
    goToInputPage() {
      const pageNumber = parseInt(this.nowPage);
      if (pageNumber >= 1) {
        this.page = pageNumber; // 선택된 페이지 업데이트
        this.getPosts(this.page - 1); // 페이지에 맞는 데이터 가져오기
      }
    },
    rowClickListener(postPk) {
      this.$router.push({
        name: "PostDetail",
        params: { postPk: postPk },
      });
    },

  },
  created() {
    this.getPosts(this.nowPage - 1);
  },
  computed: {
    ...mapGetters(["posts"]),
  },
};
</script>
