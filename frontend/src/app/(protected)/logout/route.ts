import { NextRequest, NextResponse } from "next/server";
import { cookies } from "next/headers";

export const GET = logout;
export const POST = logout;

async function logout(request: NextRequest) {
  const cookieStore = await cookies();
  cookieStore.delete("token");

  return NextResponse.redirect(new URL("/login", request.url));
}
