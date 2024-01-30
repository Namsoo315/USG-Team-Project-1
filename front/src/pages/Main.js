import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableHead, TableRow} from '@mui/material';
import axios from 'axios';
const Main = () => {
    const [data, setData] = useState([]); // 데이터를 저장할 상태

  useEffect(() => {
    // 서버에서 데이터를 가져오는 비동기 함수 (axios 또는 fetch 등 사용)
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8081/api/location/home'); 
        const result = response.data;
        console.log(result);
        setData(result.data); // 데이터를 상태에 설정
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData(); // 데이터를 가져오는 함수 호출

  }, []); // 빈 배열을 전달하면 컴포넌트가 마운트될 때 한 번만 실행

  if (!data) {
    return <div>Loading...</div> // 데이터를 받을 때까지 로딩 표시
  }

  return (
    <Table border='1'>
    <TableHead>
      <TableRow>
        <TableCell colSpan="4">명소</TableCell>
      </TableRow>
      <TableRow>
        <TableCell>Name</TableCell>
        <TableCell>ID</TableCell>
        <TableCell>Comment</TableCell>
        <TableCell>Image</TableCell>
      </TableRow>
    </TableHead>
    <TableBody>
      {data.map(item => (
        <TableRow key={item.id}>
          <TableCell>{item.name}</TableCell>
          <TableCell>{item.id}</TableCell>
          <TableCell>{item.comment || 'No comment'}</TableCell>
          <TableCell>
            {/* 이미지 표시 */}
            {item.imgUrl ? (
              <img src={`data:image/jpeg;base64, ${item.imgUrl}`} alt={'Image for ${item.name}'} style={{ maxHeight: '300px'}} />
            ) : (
              'No image available'
            )}
          </TableCell>
        </TableRow>
      ))}
    </TableBody>
  </Table>
  );
}

export default Main;