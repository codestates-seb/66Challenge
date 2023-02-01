import axios from 'axios';

export default function Pushtest() {
  const a = () => {
    // axios.get(`${process.env.NEXT_PUBLIC_API_URL}/api/autopush`);
    axios.get(`${process.env.NEXT_PUBLIC_API_URL}/api/autopush`);
  };
  return <div onClick={a}>click</div>;
}
