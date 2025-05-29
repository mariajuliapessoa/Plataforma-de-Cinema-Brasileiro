"use client"

import type React from "react"
import { Camera, Eye, EyeOff, LoaderCircle, MoveRight } from "lucide-react"
import Link from "next/link"
import { useState } from "react"
import { motion } from "framer-motion"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Checkbox } from "@/components/ui/checkbox"
import { toast } from "sonner"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import authService from "@/services/auth.service"
import { useRouter } from "next/navigation"
import Image from "next/image"

const Login = () => {
  const [showPassword, setShowPassword] = useState(false)
  const [username, setUsername] = useState<string>("")
  const [password, setPassword] = useState<string>("")
  const [isLoading, setIsLoading] = useState(false)

  const router = useRouter()

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault()
    setIsLoading(true)

    if (username === "" || password === "") {
      toast.error("Ops... Você deixou algum campo vazio.")
      setIsLoading(false)
      return
    }
    try {
      const token = await authService.login({ nomeUsuario: username, senha: password })
      toast.success("Login realizado com sucesso!")
      router.push("/main-page")
    } catch (error: any) {
      console.error(error)
      toast.error(error.message)
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="min-h-screen flex flex-col md:flex-row bg-black/[0.96] antialiased bg-grid-white/[0.02] relative overflow-hidden">
      <div className="absolute inset-0 w-full h-full bg-[radial-gradient(ellipse_at_top_right,_var(--tw-gradient-stops))] from-spektr-cyan-50/20 via-black/5 to-transparent pointer-events-none"></div>
      <motion.div
        className="absolute -z-10 h-[300px] w-[300px] rounded-full bg-spektr-cyan-50/20 blur-3xl"
        initial={{ x: -100, y: -100 }}
        animate={{
          x: [-100, 100, -100],
          y: [-100, 200, -100],
        }}
        transition={{
          repeat: Number.POSITIVE_INFINITY,
          duration: 20,
          ease: "easeInOut",
        }}
      />
      <motion.div
        className="absolute -z-10 h-[250px] w-[250px] rounded-full bg-spektr-cyan-50/10 blur-3xl right-20 top-20"
        initial={{ x: 100, y: 100 }}
        animate={{
          x: [100, -100, 100],
          y: [100, 300, 100],
        }}
        transition={{
          repeat: Number.POSITIVE_INFINITY,
          duration: 25,
          ease: "easeInOut",
        }}
      />

      <motion.div
        className="bg-[#18181b] w-full md:w-[55%] border-b md:border-b-0 md:border-r border-[#2c2c30] min-h-screen flex flex-col"
        initial={{ opacity: 0, x: -50 }}
        animate={{ opacity: 1, x: 0 }}
        transition={{ duration: 0.5 }}
      >
        <div className="p-8 md:p-12">
          <Link href="/" className="flex items-center space-x-2">
            <motion.div whileHover={{ rotate: 15 }} transition={{ type: "spring", stiffness: 300 }}>
              <Camera className="w-6 h-6 md:w-11 md:h-11 text-primary" />
            </motion.div>
            <span className="text-white text-xl font-bold">Bracine</span>
          </Link>
        </div>

        <motion.div
          className="flex justify-center"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.3, duration: 0.5 }}
        >
          <div className="w-full max-w-2xl mt-10">
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.5, duration: 0.5 }}
              className="relative overflow-hidden rounded-[2.5rem] aspect-[4/3] w-full"
            >
              <div className="absolute inset-0 bg-gradient-to-br from-spektr-cyan-50/80 to-spektr-cyan-50/60 mix-blend-overlay z-10 rounded-[2.5rem]"></div>
              <Image
                src="/mulher.png"
                alt="Login visual"
                fill
                priority
                className="object-cover rounded-[2.5rem] transform hover:scale-105 transition-transform duration-700 ease-in-out"
              />
              <div className="absolute bottom-0 left-0 right-0 p-8 z-20 bg-gradient-to-t from-black/70 to-transparent rounded-b-[2.5rem]">
                <h2 className="text-white text-2xl md:text-3xl font-bold mb-2">Bem-vindo ao Bracine</h2>
                <p className="text-white/80 text-sm md:text-base">O lugar para o cinema brasileiro</p>
              </div>
            </motion.div>
          </div>
        </motion.div>
      </motion.div>

      {/* Right section */}
      <motion.div
        className="w-full md:w-[45%] min-h-screen flex items-center justify-center p-8 md:p-12"
        initial={{ opacity: 0, x: 50 }}
        animate={{ opacity: 1, x: 0 }}
        transition={{ duration: 0.5 }}
      >
        <Card className="w-full max-w-md bg-[#18181b]/80 backdrop-blur-sm border-[#2c2c30] shadow-xl">
          <CardHeader>
            <CardTitle className="text-white text-2xl">Bem-vindo de volta</CardTitle>
            <CardDescription className="text-gray-400">Insira suas credencias para acessar sua conta</CardDescription>
          </CardHeader>
          <CardContent>
            <form>
              <div className="space-y-4">
                <div className="space-y-2">
                  <label className="text-sm font-medium text-gray-300" htmlFor="user">
                    Usuário
                  </label>
                  <motion.div whileHover={{ scale: 1.01 }} whileTap={{ scale: 0.99 }}>
                    <Input
                      id="user"
                      placeholder="Seu usuário"
                      type="text"
                      className="bg-[#27272a]/80 border-[#3f3f46] text-white focus:border-spektr-cyan-50/50 focus:ring-spektr-cyan-50/30"
                      onChange={(e) => setUsername(e.target.value)}
                    />
                  </motion.div>
                </div>
                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <label className="text-sm font-medium text-gray-300" htmlFor="password">
                      Senha
                    </label>
                    <Link href="/forgot-password" className="text-sm text-spektr-cyan-50 hover:text-spektr-cyan-50/80">
                      Esqueceu sua senha?
                    </Link>
                  </div>
                  <motion.div whileHover={{ scale: 1.01 }} whileTap={{ scale: 0.99 }} className="relative">
                    <Input
                      id="password"
                      placeholder="••••••••"
                      type={showPassword ? "text" : "password"}
                      className="bg-[#27272a]/80 border-[#3f3f46] text-white focus:border-spektr-cyan-50/50 focus:ring-spektr-cyan-50/30"
                      onChange={(e) => setPassword(e.target.value)}
                    />
                    <div
                      className="absolute right-3 top-1/2 transform -translate-y-1/2 cursor-pointer"
                      onClick={() => setShowPassword((prev) => !prev)}
                    >
                      {showPassword ? <Eye className="text-white" /> : <EyeOff className="text-white" />}
                    </div>
                  </motion.div>
                </div>
                <div className="flex items-center space-x-2">
                  <Checkbox id="remember" className="border-[#3f3f46] data-[state=checked]:bg-spektr-cyan-50" />
                  <label
                    htmlFor="remember"
                    className="text-sm font-medium leading-none text-gray-300 peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                  >
                    Lembrar de mim
                  </label>
                </div>
                <motion.div whileHover={{ scale: 1.02 }} whileTap={{ scale: 0.98 }} className="pt-2">
                  <Button
                    onClick={handleLogin}
                    className="w-full bg-primary hover:bg-primary/90 text-primary-foreground cursor-pointer duration-300 ease-in-out flex items-center justify-center gap-2"
                  >
                    {isLoading ? (
                      <div className="flex flex-row gap-2 items-center">
                        <LoaderCircle className="text-white w-6 h-6 animate-spin" /> Carregando...
                      </div>
                    ) : (
                      <>
                        Entrar <MoveRight className="w-4 h-4" />
                      </>
                    )}
                  </Button>
                </motion.div>
              </div>
            </form>
          </CardContent>
          <CardFooter className="flex flex-col space-y-4 border-t border-[#2c2c30] pt-4">
            <p className="text-center text-sm text-gray-400">
              Não possui uma conta?{" "}
              <Link href="/register" className="text-spektr-cyan-50 underline hover:text-spektr-cyan-50/80 font-medium">
                Cadastre-se
              </Link>
            </p>
          </CardFooter>
        </Card>
      </motion.div>
    </div>
  )
}

export default Login
