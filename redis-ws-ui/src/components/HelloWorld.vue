<template>
  <div class="hello">
    <h2 class="status-h2" :class="{ connect: wsStatus, disconnect: !wsStatus }">
      {{ wsStatus ? "CONNECT" : "DISCONNECT" }}
    </h2>
    <button @click="sendMessage">send</button>
    <ul class="wrapper">
      <li class="item" v-for="(item, index) in messages" :key="index">
        {{ item }}
      </li>
    </ul>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "@stomp/stompjs";

let stompClient = null;
let timer = null;

export default {
  name: "HelloWorld",
  props: {
    msg: String,
  },
  mounted: function() {
    this.$nextTick(function() {
      this.wsConnect();
    });
  },
  data: function() {
    return {
      messages: [],
      wsStatus: false,
    };
  },
  methods: {
    wsConnect() {
      // console.log("socket connect..");
      // return
      let $vm = this;

      // let socket = new SockJS("http://localhost:10000/ws");
      let socket = new SockJS("http://localhost:9000/ws");
      stompClient = Stomp.over(socket);
      stompClient.connect(
        {},
        function(frame) {
          // console.log("Connected: " + frame);
          clearInterval(timer);
          $vm.wsStatus = true;

          stompClient.subscribe("/sub/recv", function(val) {
            // console.log("/recv");
            // console.log(val);
            $vm.messages.push(val.body);
          });
          // stompClient.subscribe("/mesvcc-fe/sub/log", function(val) {
          stompClient.subscribe("/sub/log", function(val) {
            // console.log("/recv");
            // console.log(val);
            $vm.messages.push(val.body);
          });
          stompClient.subscribe("/sub/notification", function(val) {
            // console.log("/recv");
            // console.log(val);
            $vm.messages.push(val.body);
          });
          stompClient.subscribe("/subscribe", function(val) {
            // console.log("/recv");
            // console.log(val);
            $vm.messages.push(val.body);
          });
        },
        function(error) {
          $vm.wsStatus = false;
          clearInterval(timer);
          // console.log("@@@@ ERR: ", error);

          $vm.messages.push("😱");
          timer = setInterval($vm.wsConnect, 3000);
        }
      );
    },
    sendMessage() {
      stompClient.send("/pub/sendMessage", {}, JSON.stringify({
        busId: 'blah-id',
        message: '샘플메세지'
      }));
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

.wrapper {
  display: flex;
  flex-direction: column;
}

.item {
  display: inline-block;
}

.connect {
  color: green;
}
.disconnect {
  color: red;
}
</style>
