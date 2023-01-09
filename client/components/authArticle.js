export function AuthArticle() {
  return (
    <div className="flex justify-center w-full h-auto py-3 box-border">
      <div className="flex flex-col items-center  w-4/5  py-1 px-2 border-solid border-2 border-subColor ">
        <div className="flex w-3/4 justify-between">
          <span className="text-xs">userName</span>
          <span className="text-xs">postTime</span>
        </div>
        <div className="w-3/4 h-44 border-solid border-2 border-subColor mb-2">
          <img
            className="w-full h-full"
            src="https://media.istockphoto.com/id/1036780592/ko/%EC%82%AC%EC%A7%84/%EC%8B%A4%ED%96%89%EC%9D%80-%EB%A7%9E%EB%8A%94-%EC%88%99%EB%B0%95-%ED%95%98%EB%8A%94-%EA%B0%80%EC%9E%A5-%EC%A2%8B%EC%9D%80-%EB%B0%A9%EB%B2%95-%EC%A4%91-%ED%95%98%EB%82%98%EC%9E%85%EB%8B%88%EB%8B%A4.jpg?s=612x612&w=0&k=20&c=bwDHMjgN3nAuS4sM5JpEY2H8nkRK7rlrKkO8z-Txs9o="
          />
        </div>
        <div className=" w-3/4 h-1/4 box-border flex flex-wrap p-px">
          <span className="text-xs break-all w-full">
            오늘도 열심히 달려버렸다
          </span>
        </div>
      </div>
    </div>
  );
}
