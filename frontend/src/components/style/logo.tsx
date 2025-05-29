import Link from "next/link";
import Image from "next/image";

export default function Logo() {
  return (
    <Link href="/">
      <Image className="hover:brightness-125 transition-all" src="/icon.svg" alt="Logo" width={50} height={50} />
    </Link>
  );
}