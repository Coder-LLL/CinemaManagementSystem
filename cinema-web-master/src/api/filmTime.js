import request from '../utils/request'

export const getAllMovieTime = () => {
  //获取会员卡数据，请求参数pageIndex、pageSize
  return request({
    url: "getAllMovieTime",
    method: 'get',
  });
};

export const getMovieTime = query => {
  //获取会员卡数据，请求参数pageIndex、pageSize
  return request({
    url: "getMovieTime",
    method: 'get',
    params: query
  });
};

//添加电影票数据
export const insertMovieTime = query => {
  return request({
    url: 'insertMovieTime',
    method: 'post',
    params: query
  })
};
//删除电影数据，请求参数：movieId
export const delMovieTime = query => {
  console.log(query)
  return request({
    url: 'delMovieTime',
    method: 'get',
    params: query
  });
};
//修改电影数据，请求参数:movieId、name、price、time
export const modifyMovieTime = query => {
  return request({
    url: 'modifyMovieTime',
    method: 'get',
    params: query
  });
};