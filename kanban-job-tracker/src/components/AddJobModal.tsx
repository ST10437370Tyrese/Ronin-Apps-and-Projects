import { useForm } from 'react-hook-form';
import { X } from 'lucide-react';
import type { Job, Status, Priority } from '../types';

interface AddJobModalProps {
  onClose: () => void;
  onAdd: (job: Job) => void;
}

interface FormData {
  company: string;
  role: string;
  status: Status;
  priority: Priority;
}

export function AddJobModal({ onClose, onAdd }: AddJobModalProps) {
  const { register, handleSubmit, formState: { errors }, setValue, watch } = useForm<FormData>({
    defaultValues: { status: 'Applied', priority: 'Medium' }
  });

  const currentPriority = watch('priority');

  const onSubmit = (data: FormData) => {
    const newJob: Job = {
      id: crypto.randomUUID(), 
      company: data.company,
      role: data.role,
      status: data.status,
      priority: data.priority,
      dateAdded: new Date().toLocaleDateString(),
    };
    onAdd(newJob);
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black/60 dark:bg-black/80 backdrop-blur-sm flex items-center justify-center z-50 p-4">
      <div className="bg-white dark:bg-[#111111] border border-gray-200 dark:border-gray-800 rounded-3xl shadow-2xl w-full max-w-md p-6 max-h-[90vh] overflow-y-auto relative">
        
        {/* NEW: Close Button */}
        <button 
          onClick={onClose}
          className="absolute top-6 right-6 text-gray-400 hover:text-gray-800 dark:hover:text-white transition-colors"
        >
          <X size={20} />
        </button>

        <h2 className="text-xl font-bold text-gray-900 dark:text-white mb-6">Add Job</h2>
        
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-400 mb-2">Platform / Company</label>
            <input 
              {...register("company", { required: "Company is required" })} 
              placeholder="e.g. Dribbble"
              className="w-full p-3 bg-gray-50 dark:bg-[#1a1a1a] border border-gray-300 dark:border-gray-700 text-gray-900 dark:text-white rounded-xl focus:ring-2 focus:ring-blue-500 outline-none placeholder-gray-400 dark:placeholder-gray-600" 
            />
            {errors.company && <span className="text-red-500 text-xs mt-1">{errors.company.message}</span>}
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-400 mb-2">Job Role</label>
            <input 
              {...register("role", { required: "Role is required" })} 
              placeholder="e.g. Frontend Developer"
              className="w-full p-3 bg-gray-50 dark:bg-[#1a1a1a] border border-gray-300 dark:border-gray-700 text-gray-900 dark:text-white rounded-xl focus:ring-2 focus:ring-blue-500 outline-none placeholder-gray-400 dark:placeholder-gray-600" 
            />
            {errors.role && <span className="text-red-500 text-xs mt-1">{errors.role.message}</span>}
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-400 mb-2">Status</label>
                <select {...register("status")} className="w-full p-3 bg-gray-50 dark:bg-[#1a1a1a] border border-gray-300 dark:border-gray-700 text-gray-900 dark:text-white rounded-xl focus:ring-2 focus:ring-blue-500 outline-none appearance-none">
                    <option value="Applied">Applied</option>
                    <option value="Interviewing">Interviewing</option>
                    <option value="Offer">Offer</option>
                    <option value="Rejected">Rejected</option>
                </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-400 mb-2">Priority</label>
              <div className="flex gap-3 p-2 bg-gray-50 dark:bg-[#1a1a1a] border border-gray-300 dark:border-gray-700 rounded-xl justify-center">
                <button type="button" onClick={() => setValue('priority', 'Low')} className={`w-5 h-5 rounded-full bg-green-500 ${currentPriority === 'Low' ? 'ring-2 ring-gray-400 dark:ring-white ring-offset-2 dark:ring-offset-[#1a1a1a]' : 'opacity-50'}`} />
                <button type="button" onClick={() => setValue('priority', 'Medium')} className={`w-5 h-5 rounded-full bg-yellow-400 ${currentPriority === 'Medium' ? 'ring-2 ring-gray-400 dark:ring-white ring-offset-2 dark:ring-offset-[#1a1a1a]' : 'opacity-50'}`} />
                <button type="button" onClick={() => setValue('priority', 'High')} className={`w-5 h-5 rounded-full bg-red-500 ${currentPriority === 'High' ? 'ring-2 ring-gray-400 dark:ring-white ring-offset-2 dark:ring-offset-[#1a1a1a]' : 'opacity-50'}`} />
              </div>
            </div>
          </div>
          
          <div className="pt-6">
            <button type="submit" className="w-full py-3 bg-black dark:bg-white text-white dark:text-black font-bold rounded-full hover:bg-gray-800 dark:hover:bg-gray-200 transition-colors">
              Submit
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}