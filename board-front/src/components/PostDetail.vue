<template>
  <div>
    <div class="card" style="width: 30rem;">
      <div class="card-body">
        <h5 class="card-title">제목 : {{ post.title }}</h5>
        <li class="list-group-item">작성자 : {{ post.postAuthor }}</li>
      </div>
      <ul class="list-group list-group-flush">
        <li class="list-group-item">글번호 : {{ post.postPk }}</li>

        <li class="list-group-item">작성일 : {{ post.createdAt }}</li>
        <li class="list-group-item">수정일 : {{ post.modifiedAt }}</li>

      </ul>
      <div class="card-body">
        <h4 class="card-text">내용</h4>
        <p class="card-text">
          {{ post.postContent }}
        </p>
        <hr>
        <h5 class="card-text">해시태그</h5>
        <div class="card-text" v-for="hashtag in post.hashtags" :key="hashtag.hashtagPk">
          <span>{{ hashtag.text }} </span>
        </div>
      </div>
      <div class="card-footer">
        <a href="#" class="card-link">글수정</a>
        <a href="#" class="card-link">글삭제</a>
        <hr>
        <h4>댓글</h4>
        <div v-for="comment in comments" :key=comment.commentPk>
          <p>작성자 : {{ comment.commentAuthor }}</p>
          <p>내용 : {{ comment.commentContent }}</p>
          <p>작성일 : {{ comment.commentAt }}</p>
          <a v-if="comment.isDeleted" href="#" class="card-link">댓글삭제</a>
          <hr>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data: function () {
    return {
      commentPage: 1,
    };
  },
  methods: {
    ...mapActions(["getPost", "getComments"]),
  },
  created() {
    const postPk = this.$route.params.postPk;
    this.getPost({ postPk });
    console.log(this.commentPage-1);
    this.getComments({ postPk, page: this.commentPage-1 });
  },
  computed: {
    ...mapGetters(["post", "comments"]),
  }

}
</script>